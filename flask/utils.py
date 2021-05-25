from datetime import datetime
import numpy as np
import pandas as pd

def get_list_of_day(key, activities):
    for act in activities:
        if act['day'] == key:
            return act['list']
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

file1 = open('activities.txt', 'r')
Lines = file1.readlines()
file1.close()

file3 = open('days_type.txt', 'r')
Lines_labels = file3.readlines()
file3.close()

def getFeatures_durationFrequencyRatio():
    activity_names = []
    processed = []
    activities = []
    data = []
    day = ''
    i = 0;


    for line in Lines:
        _list  = line.split()

        # if _list[1] > _list[2]:
        #     print('ATTENTION')
        #     print(line)
        # if prev_line != [] and prev_line[0] == _list[0] and prev_line[2] > _list[1]:
        #     print('ATTENTION see prev line')
        #     print(line)

        # prev_line = _list

        if (_list[3] in activity_names) == False:
            activity_names.append(_list[3])

        if day == _list[0] and i != 0: 
            get_list_of_day(day, activities).append({'activity': _list[3], 'start_time': _list[1], 'end_time':_list[2]})
        else:
            day = _list[0]
            processed.append([day])  
            activities.append({'day':day, 'list':[{'activity': _list[3], 'start_time': _list[1], 'end_time':_list[2]}]})
        i += 1

    FMT = '%H:%M:%S'

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
    for line in Lines_labels:
        _list  = line.split()
        y.append(1 if 'anomalous' == _list[1] else 0)

    features = {'data': x, 'labels': y}
    return features
