#!/usr/bin/env bash
protoc --java_out=src/main/java src/protobuf/Student.proto
protoc --java_out=src/main/java src/protobuf/Person.proto