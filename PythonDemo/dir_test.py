import os
print(os.path.abspath('.'))
print(os.path.abspath('..'))
print(os.path.exists('/Users'))
print(os.path.isdir('/Users'))
print(os.path.join('/tmp/a','b'))

from pathlib import Path
p = Path('.')
print(p.resolve())

print(p.is_dir())

q = Path('/tmp/a/b/c')
Path.mkdir(q,parents=True)
