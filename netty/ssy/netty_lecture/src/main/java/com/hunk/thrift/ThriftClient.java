package com.hunk.thrift;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

@Slf4j
public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);
        try {
           transport.open();

            Person person = client.getPersonByUsername("张三");
            log.info(person.getUsername());
            log.info(person.getAge()+"");
            log.info(person.isMarried()+"");

            log.info("---------------------------------------");

            Person person2 = new Person();
            person2.setUsername("李四");
            person2.setAge(30);
            person2.setMarried(true);

            client.savePerson(person2);

        }catch (Exception e){
            log.error("Thrift call error: ",e);
        }
    }
}
