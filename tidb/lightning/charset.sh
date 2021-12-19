# 处理编码
find /tmp/test -type f | xargs grep 'utf8 ' | awk -F: '{print $1}' | xargs sed -i "s|utf8 |utf8mb4 |g"

find /tmp/test -type f | xargs grep 'utf8mb3' | awk -F: '{print $1}' | xargs sed -i "s|utf8mb3|utf8mb4|g"

find /tmp/test -type f | xargs grep 'utf8_bin' | awk -F: '{print $1}' | xargs sed -i "s|utf8_bin|utf8mb4_0900_ai_ci|g"

#find /tmp/test -type f | xargs grep 'utf8_general_ci' | awk -F: '{print $1}' | xargs sed -i "s|utf8_general_ci|utf8mb4_0900_ai_ci|g"

#find /tmp/test -type f | xargs grep 'utf8mb4_0900_ai_ci' | awk -F: '{print $1}' | xargs sed -i "s|utf8mb4_0900_ai_ci|utf8mb4_bin|g"
