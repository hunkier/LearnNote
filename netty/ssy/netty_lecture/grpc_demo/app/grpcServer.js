var PROTO_FILE_PATH = 'G:\\Users\\hunk\\IdeaProjects\\LearnNote\\netty\\ssy\\netty_lecture\\src\\main\\proto\\Student.proto';
var grpc =  require('grpc');
var grpcService = grpc.load(PROTO_FILE_PATH).com.hunk.proto;


var server = new grpc.Server();

server.addService(grpcService.StudentService.service,{
    getRealNameByUsername: getRealNameByUsername,
    getStudentsByAge: getStudentsByAge,
    getStudentsWrapperByAges: getStudentsWrapperByAges,
    biTalk: biTalk,
});
server.bind('localhost:8899', grpc.ServerCredentials.createInsecure());
server.start();

function getRealNameByUsername(call, callback) {

    console.log("username: " + call.request.username)
    console.log("call: " + JSON.stringify(call))

    callback(null, {realname: '张三'})
}

function getStudentsByAge() {}
function getStudentsWrapperByAges() {}
function biTalk() {}
