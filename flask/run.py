# Dependencies
from flask import Flask, request, jsonify
import pickle 
# Your API definition
app = Flask(__name__)

@app.route('/predict/day', methods=['POST'])
def predict_day():
    try:
        user_input = request.json
        model = pickle.load(open('anomaly_detection.pkl', 'rb'))
        prediction = 'normal' if model.predict(user_input)[0] == 0 else 'anomalous'

        return jsonify({'prediction': prediction})

    except:
        return jsonify({'trace': traceback.format_exc()})

@app.route('/predict/disease', methods=['POST'])
def predict_disease():
    try:
        user_input = request.json
        model = pickle.load(open('disease_detection.pkl', 'rb'))
        prediction = model.predict(user_input)[0]
        
        return jsonify({'disease': prediction})

    except:
        return jsonify({'trace': traceback.format_exc()})

if __name__ == '__main__':
    app.run(debug=True)