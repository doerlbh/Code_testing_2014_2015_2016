%% HW3 for MATH381
% @Author: Baihan Lin
% @Date: Oct 2016

clear all; close all; clc;

% initialization
max = 12;
V = [1:max]
E = [];

% get edges and the corresponding graph appears
for j = 1:max
    for k = j:max
        if (isprime(j^2+k^2+j*k+4))
            E = [E;[j k]];
        end
    end
end

% show edges
E



fid = fopen('/Users/DoerLBH/Dropbox/git/Code_testing/M381/lp3.txt','wt');
fprintf(fid, 'Happy');
fclose(fid);

