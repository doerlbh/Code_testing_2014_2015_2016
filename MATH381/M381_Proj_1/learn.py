# 1 - Installing Python

print "Hello, World!"

# 2 - Numbers and Math

2+2
6-3
18/3
18/7
18.0/7
18/7.0
18/7.
18%7
8.75%0.5
6**7
8**2
-5**2

# 3 - Variables

x = 18
x + 15
X ** 3
y = 54
X + y

g = input("Enter number here: ")
43
g + 32

# 4 - Modules and Functions

5 ** 4
pow(5,4)
abs(-18)

import math
math.floor(18.4)
math.sqrt(81)

bucky = math.sqrt
bucky(9)

# 5 - Save your programs

x = raw_input("Enter name: ")
print "Hey " + x
raw_input("Press<enter>")

# 6 - Strings

"hey now"
"he's a jerk"
"he said\"adfad\" hahaha"

a = "buc"
b = "rob"
a+b
a,b

# 7 - More on Strings

num = str(18)
print "bucky is " + num

num2 = 32
print "my mom is " + `num2`

repr(32)


# 8 - Raw input

buck = input("Enter name: ")
# not necessary string
raw_input("Enter name: ")
# always change into string

# 9 - Sequences and Lists

family = ['mom','dad','bro','sis','dog']
family[3]
family[-1]

'bucky'[3]

#10 - Slicing

example = [0,1,2,3,4,5,6,7,8,9]

example[4:8]
example[-5:-1]
example[-5:]
example[:]
example[1:8:2]
example[10:0:-2]
example[::-2]

#11 - Editing Sequences

[7,4,5]+[4,6,5]
'bucky' + 'roberts'
'bucky'*10
21*10
[21]*10

name = 'roastbeef'
'z' in name
'r' in name

family = ['mom', 'dad', 'bro']
'sis' in family
'mom' in family

# 12 - More List Functions

numbers = [8,1,4,17,28,165,7]
len(numbers)
max(numbers)
min(numbers)

list('bucky')

numbers
numbers[3] = 77
numbers
del numbers[3]

# 13 - Slicing Lists

example = list('easyhoss')
example

example[4:]= list('baby')
example

example[4:]= list('racecars')
example

example = [7,8,9]
example

example[1:1] = [3,3,3]
example

example[1:5] = []
example

# 14 - Intro to Methods

face = [21,18,30]
face
face.append
face

apples = ['i', 'love', 'apples', 'apples', 'now']
apples

apples.count('apples')
apples.count('bow')

one = [1,2,3]
one

two = [4,5,6]

two

one.extend(two)

# 15 - More Method

say = ['hey', 'now', 'brown', 'cow']
say
say.index('brown')
say.insert(2,'hoss')
say
say.pop(1)
say
say.remove('brown')
say
say.reverse()
say

#16 - Sort and Tuples

new = [32, 54, 22, 7, 98, 1]
new
new.sort()
new

sorted('Easyhoss')

41, 42, 32, 54
bucky = (32, 43, 43, 54)
bucky
bucky[2]

#15 - String n Stuff

bucky = "Hey there %s, hows your %s"
varb = ('betty', 'foot')
print bucky % varb
varc = ('tuna','fin')
print bucky % varc

example = "Hey now bessie nice chops"
example
example.find('bessie')
example.find('chops')

seperator = 'hoss'

# 18 - Cool String Methods

sequence = ['Hey', 'there', 'bessie','hoss']
sequence
glue = 'hoss'
glue.join(sequence)

randstr="I wish i had NO sausage"
randstr
randstr.lower()

truth = "I love old women"
truth
truth.replace('women', 'men')

# 19 - Dictionary

book = ('Dad':'Bob', 'Mom':'Lisa', 'Bro':'Joe')
book
book('Dad')
ages = ('Dad':'42', 'Mom':'87')
ages
ages('Dad')
book.clear()
book
tuna=ages.copy()
tuna
tuna.has_key('Mom')
tuna.has_key('Apples')

# 20 - If Statement

tuna="fish"
if tuna=="fish":
    print 'this is a fish alright'
    

if tuna=="bass":
    print 'this is a bass alright'

# 21 - else and elif

tuna="fish"
if tuna=="bass":
    print 'this is a bass alright'
else:
    print 'I dont know what this is'

tuna="fish"
if tuna=="bass":
    print 'this is a bass alright'
elif tuna=='salmon':
    print 'I hope I dont get poison'
elif tuna=='fish':
    print 'I know it is fish'
else:
    print 'I dont know what this is'

# 22 - Nesting Statements

thing = "animal"
animal = "cat"

if thing == "animal":
    if animal == "cat":
        print 'this is a cat'
    else:
        print "Idk what animal this is"
else:
    print "idk what this is"

    if thing == "animal":
    if animal == "dog":
        print 'this is a dog'
    else:
        print "Idk what animal this is"
else:
    print "idk what this is"

# 23 - Comparison Operators

9 < 7
9<= 9
9!=3

one = [21, 22, 23]
two = [21, 22, 23]
one == two
one is two
one is one

three = four = [2, 3, 4]
three is four

pizza = "pizzahut"
'p' in pizza
'z' in pizza

# 24 - And and Or

"dog" < "cat"
example  = 5
if example > 3 and example < 10:
    print 'num is between 3 and 10'
    
if example > 3 and example < 4:
    print 'num is between 3 and 4'

if example > 3 or example < 4:
    print 'this works'

# 25 - For and While Loops

b = 1
b
while b<=10:
    print b
    b+=1

g1 = ['bread', 'milk', 'meat', 'beef']
g1
for food in g1:
    print 'I want ' + food

# 26 - Infinite Loops and Break

ages = {'dad':42, 'mom':48, 'lisa':7}
ages
for item in ages:
    print item

for item in ages:
    print item, ages[item]

while 1:
    name = raw_input('Enter name: ')
    if name == 'quit': break

# 27 - Building Functions

def whatsup(x):
    return 'whats up ' + x

print whatsup('tony')

def plusten(y):
    return y+10

print plusten(44)

# 28 - Default Parameters

def name(first, last):
    print '%s %s' % (first, last)

name('Baihan', 'Lin')

def name(first = 'tom', last = 'smith'):
    print '%s %s' % (first, last)

name()
name('bucky')

# 29 - Multiple Parameters

def list(*food):
    print food

list('apples')
list('apples', 'peaches', 'beef')

def profile(name, *ages):
    print name
    print ages

profile('bucky', 42, 43, 76, 54, 98)

# 30 - Parameter Types

def cart(**items):
    print items

cart(apples=4,peaches=6,beef=60)

def profile(first,last,*ages, **items):
    print first, last
    print ages
    print items

profile('buc', 'rob', 32,43,76,65,47, bacon=4, saus=64)

# 31 - Tuples as Parameters

def example(a,b,c):
    return a+b*c

tuna=(5,7,3)
example(*tuna)

def example2(**this):
    print this

bacon=('mom':32,'dad':54)
example(**bacon)

# 32 - Object Oriented Program

class exampleClass:
    eyes="blue"
    age=22
    def thisMethod(self):
        return 'hey this method worked'

exampleClass
exampleObject = exampleClass()
exampleObject.eyes
exampleObject.age
exampleObject.thisMethod()

# 33 - Classes and Self

class className:
    def createName(self, name):
        self.name=name
    def displayName(self):
        return self.name
    def saying(self):
        print "hello %s" % self.name

className
first=className()
second=className()
first.createname('bucky')
second.createname('tony')
first.saying()
first.displayName()
first.name

# 34 - Subclasses Superclasses

class parentClass:
    varl="i am var1"
    var2="i am var2"

class childClass(parentClass):
    pass

parentObject=parentClass()
parentObject.var1
childObject=childClass()
childObject.var2

# 35 - overwrite Variable on Sub

class parent:
    varl="bacon"
    var2="jazz"

class child(parent):
    var2="toast"

pop=parent()
cob=child()
pob.var1
pob.var2
cob.var1
cob.var2

# 36 - Multiple Parent Classes

class Mom:
    var1="hey im mom"

class Dad:
    var3="hey son im adad"

class child(Mom,Dad):
    var3="I am a new var"

childObj = child()
childObj.var1
childObj.var2
childObj.var3

# 37 - Constructors

class swine:
    def apples(self):
        ptint "beef pie"

obj = swine()
obj.apples()

class new:
    def __init__(self):
        print "this is a constructor"
        print "this also print out"

newobj = new();

# 38 - Import Modules

-# creat module

#def testmod():
#    print "this baby worked"

# import swineflu
# swineflu.testmod()

# we can only import once in each session

# 39 - reload Modules

# baby=swineflu.testmod
# baby()

# reload(swineflu)

# 40 - Getting Module Info

import math
math.sqrt(81)
dir(math)
help(math)

math.__doc__

# 41 - Working with Files

fob=open('c:/test/a.txt','w')
fob.write('hey now brown cow')
fob.close()

fob=open('c:/test/a.txt','r')
fob.read(3)
fob.read()
fob.close()

# 42 - Reading and Writing

fob=open('c:/test/a.txt','r')
print fob.readline()
print fob.readlines()
fob.close()
fob=open('c:/test/a.txt','w')
fob.write('this is line\nthis is line2\nthis is final line')
fob.close()

# 43 - Writing Lines

fob=open('c:/test/a.txt','r')
listme=fob.readlines()
listme
fob.close()
listme[2]="mmm i sure love bacon"
listme
fob=open('c:/test/a.txt','w')
fob.writelines(listme)
fob.close()


raw_input("Press<enter>")

