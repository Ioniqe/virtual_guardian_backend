# Dependencies
from flask import Flask, request, jsonify
import pickle 
from flask_cors import CORS

from utils import getFeatures_duration, getFeatures_durationFrequencyRatio, getFeatures_duration_forDay, getFeatures_frequency, getFeatures_frequency_forDay, get_features_from_days
from utils import getFeatures_durationFrequencyRatio_forDay

from sklearn.linear_model import LogisticRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn import svm
from sklearn.naive_bayes import GaussianNB

from flask_mysqldb import MySQL
import pickle
from datetime import datetime   

app = Flask(__name__)
CORS(app)

app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = 'ioniqe'
app.config['MYSQL_DB'] = 'virtual_angel'

mysql = MySQL(app)

# model_type_in_training = 'logisticRegression'
# model_in_training = pickle.load(open('anomaly_detection.pkl', 'rb'))
# features_for_training = 'durationFrequencyRatio'

# model_type_in_use = 'logisticRegression'
# model_in_use = pickle.load(open('anomaly_detection.pkl', 'rb'))
# features_in_use = 'durationFrequencyRatio'

model_type_in_training = ''
model_in_training = ''
features_for_training = ''

model_type_in_use = ''
model_in_use = ''
features_in_use = ''

@app.route('/train_model', methods=['POST'])
def train_model():
    try:
        user_input = request.json

        cur = mysql.connection.cursor() 
        cur.execute("SELECT * FROM labeled_days")
        labeledDays = cur.fetchall()
 
        user_input_algorithm = user_input['algorithm']
        user_input_features = user_input['features']

        cur.execute("SELECT * FROM ml_variables WHERE model_type_in_use = \'" + user_input_algorithm + "\' AND features_in_use = \'" + user_input_features + "\' ORDER BY date_added DESC LIMIT 1")
        existing_model = cur.fetchall()

        x_train = []
        x_test = []
        y_train = []
        y_test = []

        global model_in_training
        global model_type_in_training
        global features_for_training

        if len(existing_model): # if model with needed algorithm and features already exists => load model
            print('ALREADY EXISTS')
            features_for_training = existing_model[0][2] # features_for_training
            model_type_in_training = existing_model[0][1] # model_type_in_training
            model_path = existing_model[0][3]  # path

            model_in_training = pickle.load(open(model_path, 'rb'))
            processed_features = get_features_from_days(user_input_features, labeledDays)
            
            x_train = processed_features['x_train']
            x_test = processed_features['x_test']
            y_train = processed_features['y_train']
            y_test = processed_features['y_test']

            features_for_training = processed_features['features_for_training']

        else: #else create model with needed algorithm and features
            print('DOESN\'T EXIST')

            processed_features = get_features_from_days(user_input_features, labeledDays)
            
            x_train = processed_features['x_train']
            x_test = processed_features['x_test']
            y_train = processed_features['y_train']
            y_test = processed_features['y_test']

            features_for_training = processed_features['features_for_training']

            if user_input['algorithm'] == "logisticRegression":
                model_in_training = LogisticRegression(random_state=0, solver='lbfgs', multi_class='ovr', max_iter=500).fit(x_train, y_train)
                model_type_in_training = 'logisticRegression'
            elif user_input['algorithm'] == "kNeighbours":
                model_in_training = KNeighborsClassifier(n_neighbors = 3).fit(x_train, y_train)
                model_type_in_training = 'kNeighbours'
            elif user_input['algorithm'] == "decisionTree":
                model_in_training = DecisionTreeClassifier(max_features = 1).fit(x_train, y_train)
                model_type_in_training = 'decisionTree'
            elif user_input['algorithm'] == "svm":
                model_in_training = svm.SVC(kernel='linear').fit(x_train, y_train) 
                model_type_in_training = 'svm'
            elif user_input['algorithm'] == "naiveBayes":
                model_in_training = GaussianNB().fit(x_train, y_train)
                model_type_in_training = 'naiveBayes'
            else: return jsonify({'score': '404'})


        print('~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~')
        print(model_in_training)
        # print(features_for_training)

        score = model_in_training.score(x_test, y_test)
        print(score)
        
        cur.close()
        return jsonify({'score': score})
    except:
        return jsonify({'score': '500'})

@app.route('/set_default', methods=['GET'])
def set_default():
    global model_type_in_training;
    global model_in_training;
    global features_for_training;
    
    global model_type_in_use;
    global model_in_use;
    global features_in_use;

    model_type_in_use = model_type_in_training
    model_in_use = model_in_training
    features_in_use = features_for_training

    time = datetime.utcnow()  

    # save on disk
    saved_model_file_name = './saved_models/model_' + time.strftime('%Y-%m-%d_%H-%M-%S') + '.pkl'
    print(saved_model_file_name)
    pickle.dump(model_in_use, open(saved_model_file_name, 'wb'))

    # save in db
    cur = mysql.connection.cursor() 
    cur.execute("INSERT INTO ml_variables(model_type_in_use, features_in_use, path, date_added) VALUES(%s, %s, %s, %s)", (model_type_in_use, features_in_use, saved_model_file_name, time.strftime('%Y-%m-%d %H:%M:%S')  ))  #model_type_in_use, features_in_use, path, date_added
    mysql.connection.commit()
    cur.close()

    return '200'

@app.route('/get_ml_variables', methods=['GET'])
def get_ml_variables():
    global model_type_in_use;
    global model_in_use;
    global features_in_use;

    # get last thing from db
    cur = mysql.connection.cursor() 
    cur.execute("SELECT * FROM ml_variables ORDER BY id DESC LIMIT 1")
    ml_variables = cur.fetchall()

    if cur.rowcount > 0:
        model_path = ml_variables[0][3]  # path
        features_in_use = ml_variables[0][2] # features_in_use
        model_type_in_use = ml_variables[0][1] # model_type_in_use
        model_in_use = pickle.load(open(model_path, 'rb'))
    else:
        features_in_use = 'durationFrequencyRatio'
        model_type_in_use = 'logisticRegression'
        model_in_use = pickle.load(open('anomaly_detection.pkl', 'rb'))

    print("----get_ml_variables model in use")
    print(model_in_use)
    print("----get_ml_variables features in use")
    print(features_in_use)
    cur.close()
    return '200'

@app.route('/predict/day', methods=['POST'])
def predict_day():  # for day from producer
    try:
        user_input = request.json

        global model_in_use;
        global features_in_use;

        print(model_in_use)
        print(features_for_training)

        features = []
        if features_in_use == 'durationFrequencyRatio':
            features = getFeatures_durationFrequencyRatio_forDay(user_input['arr'])
        elif features_in_use == 'duration':
            features = getFeatures_duration_forDay(user_input['arr'])
        elif features_in_use == 'frequency':
            features = getFeatures_frequency_forDay(user_input['arr'])
        else: return jsonify({'prediction': 'feature type not found'})

        prediction = 'normal' if model_in_use.predict([features])[0] == 0 else 'anomalous'  #trebe [0]

        print(user_input['arr'][0]['day'])
        print('PREDICTION')
        print(prediction)

        return jsonify({'prediction': prediction})
    except:
        print('EXCEPTION')
        return jsonify({'prediction': 'unexpected error'})

@app.route('/predict/days', methods=['POST'])
def predict_days(): # for experiments page
    try:
        user_input = request.json
        
        global model_in_training;
        global features_for_training;

        print(model_in_training)
        print(features_for_training)

        features = [] 
        if features_for_training == 'durationFrequencyRatio':
            for daysActivities in user_input:
                features.append(getFeatures_durationFrequencyRatio_forDay(daysActivities['activities']))
        elif features_for_training == 'duration':
            for daysActivities in user_input:
                features.append(getFeatures_duration_forDay(daysActivities['activities']))
        elif features_for_training == 'frequency':
            for daysActivities in user_input:
                features.append(getFeatures_frequency_forDay(daysActivities['activities']))
        else: return jsonify({'predictions': 'feature type not found'})

        results = model_in_training.predict(features)

        pretty_results = []
        for i in range(len(results)):
            pretty_results.append({'day': user_input[i]['day'], 'result': 'anomalous' if results[i] == 1 else 'normal'})

        return jsonify({'predictions': pretty_results})
    except:
        print('EXCEPTION')
        return jsonify({'predictions': 'unexpected error'})

@app.route('/predict/disease', methods=['POST']) #TODO modify classif alg for disease detection to kNN 
def predict_disease():
    try:
        user_input = request.json
        disease_model = pickle.load(open('disease_detection.pkl', 'rb'))
        prediction = disease_model.predict(user_input)[0]
        
        print('PREDICTION')
        print(prediction)

        return jsonify({'disease': prediction})

    except:
        return jsonify({'disease': 'unexpected error'})

if __name__ == '__main__':
    app.run(debug=True)





