import os

def isPath(path):
    if not os.path.exists(path):
        os.mkdir(path)