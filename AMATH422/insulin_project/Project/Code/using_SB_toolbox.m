%script for using system biology tool box

%run paper=SBedit and input the following chuck of text
% ********** MODEL NAME            
% IR_saturating_insulin_from_paper 
%                                  
% ********** MODEL NOTES           
%                                  
%                                  
% ********** MODEL STATES          
% d/dt(x1) = 0                     
% d/dt(x2) = k25*x5-(k32+k72)*x2   
% d/dt(x3) = k32*x2-k43*x3         
% d/dt(x4) = k43*x3-k64*x4         
% d/dt(x5) = k56*x6+k57*x7-k25*x5  
% d/dt(x6) = k64*x4-k56*x6         
% d/dt(x7) = k72*x2-k57*x7         
%                                  
% x1(0) = 0                        
% x2(0) = 100                      
% x3(0) = 0                        
% x4(0) = 0                        
% x5(0) = 0                        
% x6(0) = 0                        
% x7(0) = 0                        
%                                  
% ********** MODEL PARAMETERS      
% k25 = 0.073700000000000002       
% k32 = 1.29                       
% k43 = 0.0212                     
% k56 = 0.10100000000000001        
% k72 = 0.041099999999999998       
% k64 = 0.23000000000000001        
% k57 = 0.23000000000000001        
%                                  
%                                  
% ********** MODEL VARIABLES       
%                                  
%                                  
% ********** MODEL REACTIONS       
%                                  
%                                  
% ********** MODEL FUNCTIONS       
%                                  
%                                  
% ********** MODEL EVENTS          
%                                  
%                                  
% ********** MODEL MATLAB FUNCTIONS
%


%or just have mat file 'paper_model.mat'

load paper_model


simulation=SBsimulate(paper,200);
ss = SBsteadystate(paper,simulation.statevalues(end,:));

Jacobian=SBjacobian(paper,ss);
%%
%sensitivity analysis
output = SBsensdatastat(paper);
SBsensstat(output) %plots sensitivities







                                 