# -*- coding: utf-8 -*-
#! python3
# csv_transpose
# To transpose csv files
# by Baihan Lin, July 2016

import webbrowser, sys, pyperclip, requests
#import subprocess

if len(sys.argv) > 1:
    # Get address from command line.
    input = ' '.join(sys.argv[1:])
else:
    # Get address from clipboard.
    input = pyperclip.paste()

import csv

from itertools import izip

a = izip(*csv.reader(open(input, "rb")))
csv.writer(open("T-" + input, "wb")).writerows(a)
