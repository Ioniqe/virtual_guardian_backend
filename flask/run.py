# Dependencies
from flask import Flask, request, jsonify
import pickle 
from flask_cors import CORS

# Your API definition
app = Flask(__name__)
CORS(app)

@app.route('/predict/day', methods=['POST'])
def predict_day():
    try:
        user_input = request.json
        print(user_input)

        if user_input['features'] == "durationFrequencyRatio" and user_input['algorithm'] == "logisticRegression":
            model = pickle.load(open('anomaly_detection.pkl', 'rb'))
            prediction = 'normal' if model.predict(user_input['arr'])[0] == 0 else 'anomalous'  #trebe [0]

            print('PREDICTION')
            print(prediction)

            return jsonify({'prediction': prediction})
        else: 
            return "combination of features and algorithm not found"
    except:
        return 500

@app.route('/predict/disease', methods=['POST'])
def predict_disease():
    try:
        user_input = request.json
        model = pickle.load(open('disease_detection.pkl', 'rb'))
        prediction = model.predict(user_input)[0]
        
        print('PREDICTION')
        print(prediction)

        return jsonify({'disease': prediction})

    except:
        return 500

if __name__ == '__main__':
    app.run(debug=True)