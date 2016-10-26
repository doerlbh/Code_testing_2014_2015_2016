#!/usr/local/bin/
# Baihan Lin
# October 2016

#cat rslist | uniq -d > rslistuniq;

cp pref.list pref.run;
sed -i -e 's/^/if [ $j = /' pref.run;
sed -i 's#$# ];then echo #' pref.run;

paste pref.run prefMF.list > pref_ind_switch.sh;
sed -i 's#$# >> new_pref.out;fi#' pref_ind_switch.sh;

cat pref_ind_switch.sh | tr -d '\011' > pref_ind_switch_final.sh;

for j in `cat FoodGroup.list`; do
	sh pref_ind_switch_final.sh
done

