#!/usr/bin/env bash
thrift --gen java  -out src/main/java src/thrift/data.thrift
