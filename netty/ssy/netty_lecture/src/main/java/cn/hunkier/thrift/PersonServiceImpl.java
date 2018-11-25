package cn.hunkier.thrift;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

@Slf4j
public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        log.info("Got Client Param: " + username);
        Person person = new Person();
        person.setUsername(username);
        person.setAge(20);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        log.info("Got Client Param: ");
        log.info(person.getUsername());
        log.info(person.getAge()+"");
        log.info(person.isMarried()+"");
    }
}
