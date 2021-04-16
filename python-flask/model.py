import numpy as np
import pandas as pd
import matplotlib as mpl
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn import model_selection,preprocessing
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
import pickle
import warnings 
warnings.simplefilter("ignore")

df = pd.read_csv("C:/Users/Narendra/Desktop/mtpexample/MarathonData.csv")
df = df.drop(['id'],axis = 1)
df = df.drop(['marathon'],axis = 1)
df = df.drop(['name'],axis = 1)
df['marathontime'].skew()
df['km4week'].skew()

for c in df.columns:
    if df[c].dtype == 'object':
        lbl = preprocessing.LabelEncoder()
        lbl.fit(list(df[c].values))
        df[c] = lbl.transform(list(df[c].values))

y_train = df['marathontime']
x_train = df.drop(['marathontime'],axis = 1)

x_train['km4week'].skew()
x_train['sp4week']=np.log(x_train.sp4week)
x_train['sp4week'].skew()

#creating test and training data
data_train, data_test, label_train, label_test = train_test_split(x_train, y_train, test_size = 0.2, random_state = 42)
#dtrain = xgb.DMatrix(data_train, label_train)

rf = RandomForestRegressor(n_estimators = 100 , oob_score = True, random_state = 42)
#train the model
rf.fit(data_train, label_train)
rf_score_train = rf.score(data_train, label_train)
#print("Training score: ",rf_score_train)
rf_score_test = rf.score(data_test, label_test)
#print("Testing score: ",rf_score_test)

# Saving model to disk
pickle.dump(rf, open('model.pkl','wb'))

# Loading model to compare the results
model = pickle.load(open('model.pkl','rb'))
