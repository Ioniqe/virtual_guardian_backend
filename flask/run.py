# Dependencies
from flask import Flask, request, jsonify
import pickle 
from flask_cors import CORS

from utils import getFeatures_durationFrequencyRatio
from utils import getFeatures_durationFrequencyRatio_forDay

from sklearn.linear_model import LogisticRegression
from sklearn.linear_model import LinearRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn import svm
from sklearn.naive_bayes import MultinomialNB

app = Flask(__name__)
CORS(app)


model_in_training = pickle.load(open('anomaly_detection.pkl', 'rb'))
features_for_training = 'durationFrequencyRatio'

model_in_use = pickle.load(open('anomaly_detection.pkl', 'rb'))
features_in_use = 'durationFrequencyRatio'

@app.route('/train_model', methods=['POST'])
def train_model():
    try:
        user_input = request.json

        global features_for_training

        features = {'data': [], 'labels': []}
        if user_input['features'] == "durationFrequencyRatio":
            features = getFeatures_durationFrequencyRatio()
            features_for_training = 'durationFrequencyRatio'
        elif user_input['features'] == "durationAndFrequency":
            print("durationAndFrequency");
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
        elif user_input['algorithm'] == "linearRegression":
            model_in_training = LinearRegression().fit(x_train, y_train) 
        elif user_input['algorithm'] == "naiveBayes":
            model_in_training = MultinomialNB().fit(x_train, y_train)
        else: return '404'

        score = model_in_training.score(x_test, y_test)
        return jsonify({'score': score})
    except:
        return jsonify({'score': 'unexpected error'})

@app.route('/test', methods=['GET'])
def set_default():
    global model_in_training;
    global features_for_training;
    global model_in_use;
    global features_in_use;

    model_in_use = model_in_training
    features_in_use = features_for_training

    return '200'

# TODO maybe save in database?

@app.route('/test', methods=['GET']) # this is to test if the global variable solution works
def test():
    global model_in_training
    global features_for_training

    print('features_anomaly_detection')

    features = getFeatures_durationFrequencyRatio()
    x_train, x_test, y_train, y_test = train_test_split(features['data'], features['labels'], test_size=0.20, random_state=0)
    score = model_in_training.score(x_test, y_test)
    return jsonify({'score': score})

@app.route('/predict/day', methods=['POST'])
def predict_day():  # only get as input the day, not the features
    try:
        user_input = request.json

        global model_in_use;
        global features_in_use;

        features = []
        if features_in_use == 'durationFrequencyRatio':
            features = getFeatures_durationFrequencyRatio_forDay(user_input['arr'])
        else: return jsonify({'prediction': 'feature type not found'})

        prediction = 'normal' if model_in_use.predict([features])[0] == 0 else 'anomalous'  #trebe [0]

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
        
        print(user_input)

        global model_in_use;
        global features_in_use;

        features = [] 
        if features_in_use == 'durationFrequencyRatio':
            for daysActivities in user_input:
                print('daysActivities')
                print(daysActivities['activities'])
                features.append(getFeatures_durationFrequencyRatio_forDay(daysActivities['activities']))
        else: return jsonify({'predictions': 'feature type not found'})

        print('FEATURES')
        print(features)

        results = model_in_use.predict(features)
        print(results)

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