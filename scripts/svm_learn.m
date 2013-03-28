function svm_learn(arg1)
[features labels] = load_mat(arg1);
hold_out = floor(size(features,1) / 5);
X = features(hold_out:end,:);
D = labels(hold_out:end,:);
Xtest = features(1:hold_out,:);
Dtest = labels(1:hold_out,:);
svms = svmtrain(X,D);
guess = zeros(hold_out,1);
for i=1:hold_out
  guess(i) = svmclassify(svms, Xtest(i,:));
end
horzcat(guess, Dtest, xor(guess, Dtest), Xtest);
1-sum(xor(guess, Dtest)) / hold_out
p = (sum(and(guess, Dtest)) / sum(guess) + sum(and(not(Dtest),not(guess))) / sum(not(guess)))*.5
r = (sum(and(guess, Dtest)) / sum(Dtest) + sum(and(not(Dtest),not(guess))) / sum(not(Dtest)))*.5
f = 2*(p*r) / (p+r)
exit
