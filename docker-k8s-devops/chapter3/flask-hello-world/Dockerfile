FROM python:2.7
LABEL maintainer="hunk<huangkuier@gmail.com>"
RUN pip install flask
COPY app.py /app/
WORKDIR /app
EXPOSE 5000
ENTRYPOINT ["/usr/local/bin/python2.7", "app.py"]