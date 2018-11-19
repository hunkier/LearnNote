var service = require('../static_codegen/proto/Student_grpc_pb.js');
var message = require('../static_codegen/proto/Student_pb.js');

var grpc = require('grpc');

var server = new grpc.Server();
server.addService(service.StudentServiceService,{
    getRealNameByUsername: getRealNameByUsername,
    getStudentsByAge: getStudentsByAge,
    getStudentsWrapperByAges: getStudentsWrapperByAges,
    biTalk: biTalk,
});
server.bind('localhost:8899', grpc.ServerCredentials.createInsecure());
server.start();

function getRealNameByUsername(call, callback) {
    console.log('request: ' + call.request.getUsername())

    var myResponse = new message.MyResponse();
    myResponse.setRealname('赵六');


    callback(null, myResponse);
}

function getStudentsByAge() {}
function getStudentsWrapperByAges() {}
function biTalk() {}
