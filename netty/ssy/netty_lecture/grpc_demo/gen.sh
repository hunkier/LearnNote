npm install -g grpc-tools
#grpc_tools_node_protoc --js_out=import_style=commonjs,binary:static_codegen/ --grpc_out=static_codegen --plugin=protoc-gen-grpc=`which grpc_tools_node_protoc_plugin` proto/Student.proto

grpc_tools_node_protoc --js_out=import_style=commonjs,binary:static_codegen/ --grpc_out=static_codegen/  proto/Student.proto
