import  mymod

mymod.print_me()


#
# brew install autopep8 或者 pip install autopep8
# 打开PyCharm
#
# File → Settings → Tools → External Tools → +
#
# 配置参数
#
# Name: autopep8
# Program: autopep8
# Arguments: --in-place --aggressive --aggressive $FilePath$
# Working directory: $ProjectFileDir$
# Output filters: $FILE_PATH$\:$LINE$\:$COLUMN$\:.*
