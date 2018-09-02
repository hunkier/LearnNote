FROM 192.168.33.2/micro-service/python-base:latest
MAINTAINER hunk huangkuier@gamil.com

ENV PYTHONPATH /
COPY message /message

ENTRYPOINT ["python", "/message/message_service.py"]