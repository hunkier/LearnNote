namespace java com.hunk.thrift.user

struct UserInfo {
    1:i32 id,
    2:string username,
    3:string password,
    4:string realname,
    5:string mobile,
    6:string email,
}

service UserService {

    UserInfo getUserById(1:i32 id);

    UserInfo getUserByName(1:string name);

    void registerUser(1:UserInfo userinfo);

}