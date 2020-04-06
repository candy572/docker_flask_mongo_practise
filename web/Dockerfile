FROM python:3

WORKDIR /home/qvantel/docker/WRK/

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

CMD [ "python", "./flask_restful_calculator.py" ]

