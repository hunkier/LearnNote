var PROTO_FILE_PATH = '../proto/Student.proto';
var grpc =  require('grpc');
var grpcService = grpc.load(PROTO_FILE_PATH).cn.hunkier.proto;

var client = new grpcService.StudentService('localhost:8899', grpc.credentials.createInsecure());

client.getRealNameByUsername({username: 'zhangsan'}, function (error, respData) {
    console.log(respData);
})