function svm_learn(arg1, arg2, arg3)
[features labels] = load_mat(arg1);
hold_out = floor(size(features,1) / 5);
X = features(hold_out:end,:);
D = labels(hold_out:end,:);
Xtest = features(1:hold_out,:);
Dtest = labels(1:hold_out,:);
%disp(X(1,:));
%disp(D(1,:));
svms = svmtrain(X,D);
guess = zeros(hold_out,1);
for i=1:hold_out
  guess(i) = svmclassify(svms, Xtest(i,:));
end
horzcat(guess, Dtest, xor(guess, Dtest), Xtest);
disp('Train set hold-out');
tp = sum(and(guess, Dtest)) / sum(guess)
tn = sum(and(not(Dtest),not(guess))) / sum(not(guess))
a = 1-sum(xor(guess, Dtest)) / hold_out
p = (sum(and(guess, Dtest)) / sum(guess) + sum(and(not(Dtest),not(guess))) / sum(not(guess)))*.5
r = (sum(and(guess, Dtest)) / sum(Dtest) + sum(and(not(Dtest),not(guess))) / sum(not(Dtest)))*.5
f = 2*(p*r) / (p+r)

learn = load(arg2, '-ascii');
learn_size = size(learn,1);
labels = zeros(learn_size,1);
for i=1:learn_size
  labels(i) = svmclassify(svms, learn(i,:));
end
dlmwrite(arg3, labels, ' ');

%exit
