from flask import Flask, redirect, url_for, jsonify, request,render_template
import pickle
import numpy as np
app = Flask(__name__)
model = pickle.load(open('model.pkl', 'rb'))

@app.route('/')
def index():
   return render_template("index.html")

@app.route('/ff',methods = ['POST', 'GET'])
def ff():
   return render_template("predict.html")

@app.route('/predict',methods = ['POST'])
def predict():
   values=[]
     
   name=request.form['name']
   #values.append(name)
   
   marathon=request.form['marathon']
   #values.append(marathon)
   
   category=request.form['category']
   if category == 'MAM':
      cat = 0
   elif category == 'WAM':
      cat = 1
   elif category == 'M40':
      cat = 2
   elif category == 'M45':
      cat = 3
   elif category == 'M50':
      cat = 4
   elif category == 'M55':
      cat = 5
   values.append(cat)
   
   km4week=request.form['km4week']
   values.append(km4week)
   
   sp4week=request.form['sp4week']
   values.append(sp4week)
   
   crosstraining=request.form['crosstraining']
   if crosstraining  == 'Ciclista 1h':
      ct1 = 0
   elif crosstraining == 'Ciclista 3h':
      ct1 = 1
   elif crosstraining == 'Ciclista 4h':
      ct1 = 2
   elif crosstraining == 'Ciclista 5h':
      ct1 = 3
   elif crosstraining == "Ciclista 13h":
      ct1 = 4
   values.append(ct1)
   
   wall21=request.form['wall21']
   values.append(wall21)
   
   finalcat=request.form['finalcat']
   if finalcat == 'A':
      fc1 = 0
   elif finalcat == 'B':
      fc1 = 1
   elif finalcat == 'C':
      fc1 = 2
   values.append(fc1)
   final_values=[np.array(values)]
   prediction=model.predict(final_values)
   result=prediction
   
   res = str(result[0])
   return {'message':'Marathon time is '+res}
if __name__ == '__main__':
   app.run(debug=True,use_reloader=False)
