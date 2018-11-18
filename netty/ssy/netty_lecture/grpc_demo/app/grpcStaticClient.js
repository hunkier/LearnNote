var service = require('../static_codegen/proto/Student_grpc_pb.js');
var message = require('../static_codegen/proto/Student_pb.js');

var grpc = require('grpc');

var client = new service.StudentServiceClient('localhost:8899', grpc.credentials.createInsecure());

var request = new message.MyRequest();
request.setUsername('wangwu');

client.getRealNameByUsername(request, function (error, respData) {
    console.log(respData.getRealname());
})