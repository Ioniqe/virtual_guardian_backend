from datetime import datetime
from sklearn.model_selection import train_test_split

def get_list_of_day(key, activities):
    for act in activities:
        if act['day'] == key:
            if('list' in act):
                return act['list']
            return act['activities']
    return []

def verify_activity_existence(activity_name, _list):
    for item in _list:
        if item['activity'] == activity_name:
            return 1
    return 0

def update_array(day, activity, duration, data):
    for i in range(len(data)):
        if day == data[i]['day']:
            for j in range(len(data[i]['list'])):
                if data[i]['list'][j]['activity'] == activity:
                    data[i]['list'][j]['duration'] += duration
                    data[i]['list'][j]['frequency'] += 1
    return data

def get_features_from_days(user_input_features, labeledDays):
    features = {'data': [], 'labels': []}
    if user_input_features == "durationFrequencyRatio":
        features = getFeatures_durationFrequencyRatio(labeledDays)
        features_for_training = 'durationFrequencyRatio'
    elif user_input_features == 'duration':
        features = getFeatures_duration(labeledDays)
        features_for_training = 'duration'
    elif user_input_features == 'frequency':
        features = getFeatures_frequency(labeledDays)
        features_for_training = 'frequency'

    x_train, x_test, y_train, y_test = train_test_split(features['data'], features['labels'], test_size=0.20, random_state=0)
    
    return {'x_train': x_train, 'x_test': x_test, 'y_train': y_train, 'y_test': y_test, 'features_for_training': features_for_training}

def get_processed_days_array():
    activity_names = []
    activities = []
    data = []
    day = ''
    i = 0;

    for line in Lines:
        _list  = line.split()

        if (_list[3] in activity_names) == False:
            activity_names.append(_list[3])

        if day == _list[0] and i != 0: 
            get_list_of_day(day, activities).append({'activity': _list[3], 'start_time': _list[1], 'end_time':_list[2]})
        else:
            day = _list[0]
            activities.append({'day':day, 'list':[{'activity': _list[3], 'start_time': _list[1], 'end_time':_list[2]}]})
        i += 1

    #-------------------------------------------------

    for activity in activities:
        day = activity['day']
        data.append({'day':day, 'list':[]})
        _list = activity['list']
        for item in _list:
            current_activity = item['activity']

            duration = datetime.strptime(item['end_time'], FMT) - datetime.strptime(item['start_time'], FMT)
            list_of_day = get_list_of_day(day, data)
            if verify_activity_existence(current_activity, list_of_day): #it already exists
                data = update_array(day, current_activity, duration.seconds, data)
            else:
                get_list_of_day(day, data).append({'activity': current_activity, 'duration':duration.seconds, 'frequency': 1})

    return {'activity_names': activity_names, 'data':data}

file1 = open('activities.txt', 'r')
Lines = file1.readlines()
file1.close()

FMT = '%H:%M:%S'

def getFeatures_durationFrequencyRatio(labeledDays):
    activity_names = []
    processed = []
    activities = []
    data = []
    day = ''
    i = 0;

    for line in Lines:
        _list  = line.split()

        if (_list[3] in activity_names) == False:
            activity_names.append(_list[3])

        if day == _list[0] and i != 0: 
            print('eeeeeeeeeeeeeeee')
            get_list_of_day(day, activities).append({'activity': _list[3], 'start_time': _list[1], 'end_time':_list[2]})
            print('==============================')
        else:
            day = _list[0]
            processed.append([day])  
            activities.append({'day':day, 'list':[{'activity': _list[3], 'start_time': _list[1], 'end_time':_list[2]}]})
        i += 1


    #-------------------------------------------------

    for activity in activities:
        day = activity['day']
        data.append({'day':day, 'list':[]})
        _list = activity['list']
        for item in _list:
            current_activity = item['activity']

            duration = datetime.strptime(item['end_time'], FMT) - datetime.strptime(item['start_time'], FMT)
            list_of_day = get_list_of_day(day, data)
            if verify_activity_existence(current_activity, list_of_day): #it already exists
                data = update_array(day, current_activity, duration.seconds, data)
            else:
                get_list_of_day(day, data).append({'activity': current_activity, 'duration':duration.seconds, 'frequency': 1})


    #-------------------------------------------------
    activity_names = sorted(activity_names)
    buckets = [float(0)] * len(activity_names)

    x = []
    for p in processed:
        p.extend(buckets)
        x.append([0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0])

    i = 0;
    for day in data:
        for item in day['list']:
            processed[i][activity_names.index(item['activity']) + 1] = round(item['duration'] / item['frequency'], 2)     # + 1 bc day is on first pos
            x[i][activity_names.index(item['activity'])] = round(item['duration'] / item['frequency'], 2)  
        i += 1    

    y = []
    for line in labeledDays:
        y.append(1 if 'anomalous' == line[2] else 0)

    features = {'data': x, 'labels': y}
    return features

def getFeatures_durationFrequencyRatio_forDay(user_input):
    activityArray = ["Breakfast", "Dinner", "Grooming", "Leaving", "Lunch", "Showering", "Sleeping", "Snack", "Spare_Time/TV", "Toileting"]
    computedArray = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
    durationOfActivities = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    frequencyOfActivities = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

    for activity in user_input:
        duration = datetime.strptime(activity['endTime'], FMT) - datetime.strptime(activity['startTime'], FMT)
        durationOfActivities[activityArray.index(activity['activity'])] += duration.seconds
        frequencyOfActivities[activityArray.index(activity['activity'])] += 1
    for i in range(len(computedArray)):
        if frequencyOfActivities[i] != 0:
            computedArray[i] = round(durationOfActivities[i] / frequencyOfActivities[i], 2) 
    return computedArray

def getFeatures_duration_forDay(user_input):
    activityArray = ["Breakfast", "Dinner", "Grooming", "Leaving", "Lunch", "Showering", "Sleeping", "Snack", "Spare_Time/TV", "Toileting"]
    durationOfActivities = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

    for activity in user_input:
        duration = datetime.strptime(activity['endTime'], FMT) - datetime.strptime(activity['startTime'], FMT)
        durationOfActivities[activityArray.index(activity['activity'])] += duration.seconds
    return durationOfActivities

def getFeatures_frequency_forDay(user_input):
    activityArray = ["Breakfast", "Dinner", "Grooming", "Leaving", "Lunch", "Showering", "Sleeping", "Snack", "Spare_Time/TV", "Toileting"]
    frequencyOfActivities = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

    for activity in user_input:
        frequencyOfActivities[activityArray.index(activity['activity'])] += 1
    return frequencyOfActivities

def getFeatures_duration(labeledDays):
    x = []


    needed_data = get_processed_days_array()

    #-------------------------------------------------
    activity_names = sorted(needed_data['activity_names'])

    data = needed_data['data']

    x = []
    i = 0
    while i < len(data):
        x.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        i += 1

    i = 0;
    for day in data:
        for item in day['list']:
            x[i][activity_names.index(item['activity'])] = item['duration']  
        i += 1   

    y = []
    for line in labeledDays:
        y.append(1 if 'anomalous' == line[2] else 0)

    print('duration')
    print(x)

    features = {'data': x, 'labels': y}
    return features

def getFeatures_frequency(labeledDays):
    x = []

    needed_data = get_processed_days_array()
    activity_names = sorted(needed_data['activity_names'])
    data = needed_data['data']

    x = []
    i = 0
    while i < len(data):
        x.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        i += 1


    i = 0;
    for day in data:
        for item in day['list']:
            x[i][activity_names.index(item['activity'])] = item['frequency']
        i += 1  

    y = []
    for line in labeledDays:
        y.append(1 if 'anomalous' == line[2] else 0)

    print('frequency')
    print(x)

    features = {'data': x, 'labels': y}
    return features

def getAllDays(): 
    activities = []
    day = ''
    i = 0;

    for line in Lines:
        _list  = line.split()

        if day == _list[0] and i != 0: 
            get_list_of_day(day, activities).append({'day': day, 'activity': _list[3], 'startTime': _list[1], 'endTime':_list[2]})
        else:
            day = _list[0]
            activities.append({'day':day, 'activities':[{'day': day, 'activity': _list[3], 'startTime': _list[1], 'endTime':_list[2]}]})
        i += 1

    return activities