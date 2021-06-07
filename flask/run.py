# Dependencies
from flask import Flask, request, jsonify
import pickle 
from flask_cors import CORS

from utils import getFeatures_duration, getFeatures_durationFrequencyRatio, getFeatures_duration_forDay, getFeatures_frequency, getFeatures_frequency_forDay
from utils import getFeatures_durationFrequencyRatio_forDay

from sklearn.linear_model import LogisticRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn import svm
from sklearn.naive_bayes import MultinomialNB

from flask_mysqldb import MySQL

app = Flask(__name__)
CORS(app)

app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = 'ioniqe'
app.config['MYSQL_DB'] = 'virtual_angel'

mysql = MySQL(app)

model_in_training = pickle.load(open('anomaly_detection.pkl', 'rb'))
features_for_training = 'durationFrequencyRatio'

model_in_use = pickle.load(open('anomaly_detection.pkl', 'rb'))
features_in_use = 'durationFrequencyRatio'

# curr = mysql.connection.cursor() 
# curr.execute("SELECT * FROM ml_variables where id = 1")
# ml_variables = curr.fetchall()

# print(ml_variables)

@app.route('/train_model', methods=['POST'])
def train_model():
    try:
        user_input = request.json

        global features_for_training

        cur = mysql.connection.cursor() 
        cur.execute("SELECT * FROM labeled_days")
        labeledDays = cur.fetchall()

        print(labeledDays)

        features = {'data': [], 'labels': []}
        if user_input['features'] == "durationFrequencyRatio":
            features = getFeatures_durationFrequencyRatio(labeledDays)
            features_for_training = 'durationFrequencyRatio'
        elif user_input['features'] == 'duration':
            print('ddd')
            features = getFeatures_duration(labeledDays) #change for more days
            features_for_training = 'duration'
        elif user_input['features'] == 'frequency':
            print('fff')
            features = getFeatures_frequency(labeledDays) #change for more days
            features_for_training = 'frequency'
        elif user_input['features'] == "durationAndFrequency":
            features_for_training = 'durationAndFrequency'

        x_train, x_test, y_train, y_test = train_test_split(features['data'], features['labels'], test_size=0.20, random_state=0)
        
        global model_in_training
        if user_input['algorithm'] == "logisticRegression":
            model_in_training = LogisticRegression(random_state=0, solver='lbfgs', multi_class='ovr', max_iter=500).fit(x_train, y_train)
        elif user_input['algorithm'] == "kNeighbours":
            model_in_training = KNeighborsClassifier(n_neighbors = 3).fit(x_train, y_train)
        elif user_input['algorithm'] == "decisionTree":
            model_in_training = DecisionTreeClassifier(max_features = 1).fit(x_train, y_train)
        elif user_input['algorithm'] == "svm":
            model_in_training = svm.SVC(kernel='linear').fit(x_train, y_train) 
        elif user_input['algorithm'] == "naiveBayes":
            model_in_training = MultinomialNB().fit(x_train, y_train)
        else: return jsonify({'score': '404'})

        score = model_in_training.score(x_test, y_test)
        
        results = model_in_training.predict(features['data'])
        print(results)

        print(score)
        return jsonify({'score': score})
    except:
        return jsonify({'score': '500'})

@app.route('/set_default', methods=['GET'])
def set_default():
    global model_in_training;
    global features_for_training;
    global model_in_use;
    global features_in_use;

    model_in_use = model_in_training
    features_in_use = features_for_training

    return '200'

@app.route('/get_ml_variables', methods=['GET'])
def get_ml_variables():
    global model_in_training;
    global features_for_training;
    global model_in_use;
    global features_in_use;

    cur = mysql.connection.cursor() 
    cur.execute("SELECT * FROM ml_variables where id = 1")
    ml_variables = cur.fetchall()

    print(ml_variables[0])

    return '200'

# TODO maybe save in database?

@app.route('/predict/day', methods=['POST'])
def predict_day():  # only get as input the day, not the features
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
            print('ddd')
            features = getFeatures_duration_forDay(user_input['arr'])
        elif features_in_use == 'frequency':
            print('fff')
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
def predict_days():
    try:
        user_input = request.json
        
        print('~~~~~~~~~~~~~~~~~~~~~~~~~~~~~')
        print(user_input)

        global model_in_training;
        global features_for_training;

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


@app.route('/predict/disease', methods=['POST'])
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





