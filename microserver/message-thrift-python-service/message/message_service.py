# coding: utf-8
from message.api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = 'imoocd@163.com'
authcode = 'aA111111'

port = '9090'


class MessageServiceHandler:

    def sendMobileMessage(self, mobile, message):
        print "sendMobileMessage, mobile:" + ", message:" + message
        return True

    def sendEmailMessage(self, email, message):
        print "sendEmailMessage, email:" + email + ", message:" + message
        messageObj = MIMEText(message, "plain", "utf-8")
        messageObj['From'] = sender
        messageObj['To'] = email
        messageObj['Subject'] = Header('慕课网邮件', 'utf-8')
        try:
            smtpObj = smtplib.SMTP('smtp.163.com')
            smtpObj.login(sender, authcode)
            smtpObj.sendmail(sender, [email], messageObj.as_string())
            print 'send mail success'
            return True
        except smtplib.SMTPException, ex:
            print 'send mail failed'
            print ex
            return False


if __name__ == '__main__':
    handler = MessageServiceHandler()
    process = MessageService.Processor(handler)
    transport = TSocket.TServerSocket("0.0.0.0", port)
    tfactory = TTransport.TFramedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(process, transport, tfactory, pfactory)
    print "python thrift server start , port: " + port
    server.serve()
    print "python thrift server exit"