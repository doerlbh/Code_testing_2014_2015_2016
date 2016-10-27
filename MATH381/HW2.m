%% HW2 for MATH381
% @Author: Baihan Lin
% @Date: Oct 2016

clear all; close all; clc;

%% Question 1

% initiate adjacency matrix A.
A = [0 0 1 0 0 0 0 0 1;
    0 0 0 1 1 0 0 0 0;
    1 0 0 0 0 0 1 0 0;
    0 1 0 0 1 0 1 0 0;
    0 1 0 1 0 1 0 0 0;
    0 0 0 0 1 0 0 1 1;
    0 0 1 1 0 0 0 0 0;
    0 0 0 0 0 1 0 0 0;
    1 0 0 0 0 1 0 0 0;];

% find minimum steps for every vertex can connect to each other (complete).
n = whenComplete(A, 1);

n  % minimum steps to reach a complete graph (if n = -1, not connected).
An = A^n % the final complete adjacency graph in n steps.


%% Question 2

% G = graph([2, 4], [2, 6], [2, 8],'OmitSelfLoops');
% G = graph([2, 10], [2, 12],'OmitSelfLoops');
% G = graph([2, 14], [2, 16], [2, 18],'OmitSelfLoops');
% G = graph([2, 20], [2, 22],'OmitSelfLoops');
% G = graph([3, 6], [3, 9], [3, 12],'OmitSelfLoops');
% G = graph([3, 15], [3, 18], [3, 21],'OmitSelfLoops');
% G = graph([2, 4], [4, 8], [4, 12],'OmitSelfLoops');
% G = graph([4, 16], [4, 20],'OmitSelfLoops');
% G = graph([5, 10], [5, 15], [5, 20],'OmitSelfLoops');
% G = graph([2, 6], [6, 12], [6, 18],'OmitSelfLoops');
% G = graph([7, 14], [7, 21],'OmitSelfLoops');
% G = graph([2, 8], [4, 8], [8, 16],'OmitSelfLoops');
% G = graph([3, 9], [9, 18],'OmitSelfLoops');
% G = graph([5, 10], [10, 20],'OmitSelfLoops');
% G = graph([11, 22],'OmitSelfLoops');
% G = graph([2, 12], [3, 12], [4, 12], [6, 12],'OmitSelfLoops');
% G = graph([2, 14], [7, 14],'OmitSelfLoops');
% G = graph([3, 15], [5, 15],'OmitSelfLoops');
% G = graph([2, 16], [4, 16], [8, 16],'OmitSelfLoops');
% G = graph([2, 18], [3, 18], [6, 18], [9, 18],'OmitSelfLoops');
% G = graph([2, 20], [4, 20], [5, 20], [10, 20],'OmitSelfLoops');
% G = graph([3, 21], [7, 21],'OmitSelfLoops');
% G = graph([2, 22], [11, 22],'OmitSelfLoops');
% %
%

Hp = [0	0	1	0	1	0	1	0	1	0	1	1	0	1	1	1	0	1;
    0	0	0	0	1	0	0	1	0	0	1	0	1	0	1	0	1	0;
    1	0	0	0	0	0	1	0	0	0	1	0	0	1	0	1	0	0;
    0	0	0	0	0	0	0	0	1	0	0	0	1	0	0	1	0	0;
    1	1	0	0	0	0	0	0	0	0	1	0	0	0	1	0	0	0;
    0	0	0	0	0	0	0	0	0	0	0	1	0	0	0	0	1	0;
    1	0	1	0	0	0	0	0	0	0	0	0	0	1	0	0	0	0;
    0	1	0	0	0	0	0	0	0	0	0	0	0	0	1	0	0	0;
    0	0	0	1	0	0	0	0	0	0	0	0	0	0	0	1	0	0;
    0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1;
    1	1	1	0	1	0	0	0	0	0	0	0	0	0	0	0	0	0;
    1	0	0	0	0	1	0	0	0	0	0	0	0	0	0	0	0	0;
    0	1	0	1	0	0	0	0	0	0	0	0	0	0	0	0	0	0;
    1	0	1	0	0	0	1	0	0	0	0	0	0	0	0	0	0	0;
    1	1	0	0	1	0	0	1	0	0	0	0	0	0	0	0	0	0;
    1	0	1	1	0	0	0	0	1	0	0	0	0	0	0	0	0	0;
    0	1	0	0	0	1	0	0	0	0	0	0	0	0	0	0	0	0;
    1	0	0	0	0	0	0	0	0	1	0	0	0	0	0	0	0	0;];

% find minimum steps for every vertex can connect to each other (complete).
nH = whenComplete(Hp, 1);

nH;  % minimum steps to reach a complete graph (if n = -1, not connected).
Hn = Hp^nH; % the final complete adjacency graph in n steps.

% find minimum distance between every two vertices.
Hd = minD(Hp, nH)

