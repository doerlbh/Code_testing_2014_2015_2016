#!/usr/local/bin/
# Baihan Lin
# October 2016

#cat rslist | uniq -d > rslistuniq;

rm pref*.run*;
rm pref_ind_switch.sh*;
rm pref_ind_switch_final.sh*;
rm new_pref.out*;

cp pref.list pref1.run;
sed -i -e 's/^/elif [[ $j = /' pref1.run;

cp prefMF.list pref2.run;
sed -i -e 's/^/ ]];then echo /' pref2.run;

paste pref1.run pref2.run > pref_ind_switch.sh;
sed -i '' 's#$# >> new_pref.out;#' pref_ind_switch.sh;
#sed -i '' 's#$#;fi;#' pref_ind_switch.sh;

#sed -i -e "s/$(print '\t')/ /" pref_ind_switch.sh;

cat pref_ind_switch.sh | tr -d '\011' > pref_ind_switch_final.sh;
#cat pref_ind_switch.sh > pref_ind_switch_final.sh;

sed -i '' "s/+/ /" pref_ind_switch_final.sh;
sed -i '' "s/+/ /" pref_ind_switch_final.sh;

# copy here:

for j in `cat FoodGroup.list`; do
	#sh pref_ind_switch_final.sh
if [[ $j = A ]];then echo A 5 3.5 >> new_pref.out;
elif [[ $j = AA ]];then echo AA 3.5 3.5 >> new_pref.out;
elif [[ $j = AB ]];then echo AB 8 2.5 >> new_pref.out;
elif [[ $j = AC ]];then echo AC 6 3.5 >> new_pref.out;
elif [[ $j = AD ]];then echo AD 4.5 3 >> new_pref.out;
elif [[ $j = AE ]];then echo AE 6.5 1.5 >> new_pref.out;
elif [[ $j = AF ]];then echo AF 2.5 4.5 >> new_pref.out;
elif [[ $j = AG ]];then echo AG 2 4 >> new_pref.out;
elif [[ $j = AI ]];then echo AI 1 4.5 >> new_pref.out;
elif [[ $j = AK ]];then echo AK 2 2 >> new_pref.out;
elif [[ $j = AM ]];then echo AM 3.5 4.5 >> new_pref.out;
elif [[ $j = AN ]];then echo AN 7 5.5 >> new_pref.out;
elif [[ $j = AO ]];then echo AO 3 3.5 >> new_pref.out;
elif [[ $j = AP ]];then echo AP 4 3.5 >> new_pref.out;
elif [[ $j = AS ]];then echo AS 4.5 4.5 >> new_pref.out;
elif [[ $j = AT ]];then echo AT 2 3 >> new_pref.out;
elif [[ $j = B ]];then echo B 4 4 >> new_pref.out;
elif [[ $j = BA ]];then echo BA 3 4 >> new_pref.out;
elif [[ $j = BAB ]];then echo BAB 3 4.5 >> new_pref.out;
elif [[ $j = BAE ]];then echo BAE 2 4.5 >> new_pref.out;
elif [[ $j = BAH ]];then echo BAH 2 4.5 >> new_pref.out;
elif [[ $j = BAK ]];then echo BAK 5 4 >> new_pref.out;
elif [[ $j = BAN ]];then echo BAN 3 3.5 >> new_pref.out;
elif [[ $j = BAR ]];then echo BAR 4.5 2.5 >> new_pref.out;
elif [[ $j = BC ]];then echo BC 4 3 >> new_pref.out;
elif [[ $j = BF ]];then echo BF 1.5 0.5 >> new_pref.out;
elif [[ $j = BFD ]];then echo BFD 5 3 >> new_pref.out;
elif [[ $j = BFG ]];then echo BFG 4.5 3 >> new_pref.out;
elif [[ $j = BFJ ]];then echo BFJ 6 6.5 >> new_pref.out;
elif [[ $j = BFP ]];then echo BFP 3.5 2.5 >> new_pref.out;
elif [[ $j = BH ]];then echo BH 4.5 4 >> new_pref.out;
elif [[ $j = BJ ]];then echo BJ 1 2.5 >> new_pref.out;
elif [[ $j = BJC ]];then echo BJC 1 3.5 >> new_pref.out;
elif [[ $j = BJF ]];then echo BJF 1 2 >> new_pref.out;
elif [[ $j = BJL ]];then echo BJL 1 2.5 >> new_pref.out;
elif [[ $j = BJP ]];then echo BJP 1 2.5 >> new_pref.out;
elif [[ $j = BJS ]];then echo BJS 1 2.5 >> new_pref.out;
elif [[ $j = BL ]];then echo BL 5 1.5 >> new_pref.out;
elif [[ $j = BN ]];then echo BN 8 6 >> new_pref.out;
elif [[ $j = BNE ]];then echo BNE 8 5 >> new_pref.out;
elif [[ $j = BNH ]];then echo BNH 7 7 >> new_pref.out;
elif [[ $j = BNS ]];then echo BNS 6.5 7 >> new_pref.out;
elif [[ $j = BP ]];then echo BP 10 8.5 >> new_pref.out;
elif [[ $j = BR ]];then echo BR 4 5.5 >> new_pref.out;
elif [[ $j = BV ]];then echo BV 3.5 3.5 >> new_pref.out;
elif [[ $j = C ]];then echo C 5.5 5.5 >> new_pref.out;
elif [[ $j = CA ]];then echo CA 5 5.5 >> new_pref.out;
elif [[ $j = CD ]];then echo CD 7 5.5 >> new_pref.out;
elif [[ $j = CDE ]];then echo CDE 7 5 >> new_pref.out;
elif [[ $j = CDH ]];then echo CDH 4 4.5 >> new_pref.out;
elif [[ $j = D ]];then echo D 4.5 5.5 >> new_pref.out;
elif [[ $j = DA ]];then echo DA 3.5 7.5 >> new_pref.out;
elif [[ $j = DAE ]];then echo DAE 4 4.5 >> new_pref.out;
elif [[ $j = DAM ]];then echo DAM 4.5 5 >> new_pref.out;
elif [[ $j = DAP ]];then echo DAP 4 5 >> new_pref.out;
elif [[ $j = DAR ]];then echo DAR 4.5 5.5 >> new_pref.out;
elif [[ $j = DB ]];then echo DB 4 5 >> new_pref.out;
elif [[ $j = DF ]];then echo DF 4 5.5 >> new_pref.out;
elif [[ $j = DG ]];then echo DG 6.5 6.5 >> new_pref.out;
elif [[ $j = DI ]];then echo DI 5 5 >> new_pref.out;
elif [[ $j = DR ]];then echo DR 7 6.5 >> new_pref.out;
elif [[ $j = F ]];then echo F 6.5 7.5 >> new_pref.out;
elif [[ $j = FA ]];then echo FA 8 8 >> new_pref.out;
elif [[ $j = FC ]];then echo FC 8 7 >> new_pref.out;
elif [[ $j = G ]];then echo G 4.5 6.5 >> new_pref.out;
elif [[ $j = GA ]];then echo GA 5 6.5 >> new_pref.out;
elif [[ $j = H ]];then echo H 4 4 >> new_pref.out;
elif [[ $j = IF ]];then echo IF 1.5 1.5 >> new_pref.out;
elif [[ $j = IFB ]];then echo IFB 1.5 1.5 >> new_pref.out;
elif [[ $j = IFC ]];then echo IFC 1.5 1.5 >> new_pref.out;
elif [[ $j = J ]];then echo J 5.5 5.5 >> new_pref.out;
elif [[ $j = JA ]];then echo JA 7 7 >> new_pref.out;
elif [[ $j = JC ]];then echo JC 5 5 >> new_pref.out;
elif [[ $j = JK ]];then echo JK 5 4.5 >> new_pref.out;
elif [[ $j = JM ]];then echo JM 5 4 >> new_pref.out;
elif [[ $j = JR ]];then echo JR 7 7 >> new_pref.out;
elif [[ $j = M ]];then echo M 6.5 5 >> new_pref.out;
elif [[ $j = MA ]];then echo MA 8.5 6 >> new_pref.out;
elif [[ $j = MAA ]];then echo MAA 6.5 3.5 >> new_pref.out;
elif [[ $j = MAC ]];then echo MAC 8.5 5.5 >> new_pref.out;
elif [[ $j = MAE ]];then echo MAE 7.5 4.5 >> new_pref.out;
elif [[ $j = MAG ]];then echo MAG 5.5 5.5 >> new_pref.out;
elif [[ $j = MAI ]];then echo MAI 5 6 >> new_pref.out;
elif [[ $j = MC ]];then echo MC 5 5 >> new_pref.out;
elif [[ $j = MCA ]];then echo MCA 5 6 >> new_pref.out;
elif [[ $j = MCC ]];then echo MCC 5.5 6.5 >> new_pref.out;
elif [[ $j = MCE ]];then echo MCE 5.5 4.5 >> new_pref.out;
elif [[ $j = MCG ]];then echo MCG 2.5 3 >> new_pref.out;
elif [[ $j = MCI ]];then echo MCI 2 3 >> new_pref.out;
elif [[ $j = MCK ]];then echo MCK 2 3.5 >> new_pref.out;
elif [[ $j = MCM ]];then echo MCM 3 3.5 >> new_pref.out;
elif [[ $j = MCO ]];then echo MCO 5 6.5 >> new_pref.out;
elif [[ $j = ME ]];then echo ME 2 4.5 >> new_pref.out;
elif [[ $j = MEA ]];then echo MEA 2.5 1.5 >> new_pref.out;
elif [[ $j = MEC ]];then echo MEC 3 1.5 >> new_pref.out;
elif [[ $j = MEE ]];then echo MEE 2 1.5 >> new_pref.out;
elif [[ $j = MG ]];then echo MG 1.5 2.5 >> new_pref.out;
elif [[ $j = MBG ]];then echo MBG 7 3.5 >> new_pref.out;
elif [[ $j = MI ]];then echo MI 7 4.5 >> new_pref.out;
elif [[ $j = MIG ]];then echo MIG 6 4 >> new_pref.out;
elif [[ $j = MR ]];then echo MR 7 4.5 >> new_pref.out;
elif [[ $j = O ]];then echo O 3 2 >> new_pref.out;
elif [[ $j = OA ]];then echo OA 2 1.5 >> new_pref.out;
elif [[ $j = OB ]];then echo OB 3.5 2 >> new_pref.out;
elif [[ $j = OC ]];then echo OC 2 2 >> new_pref.out;
elif [[ $j = OE ]];then echo OE 4.5 2.5 >> new_pref.out;
elif [[ $j = OF ]];then echo OF 1.5 2 >> new_pref.out;
elif [[ $j = P ]];then echo P 5.5 4.5 >> new_pref.out;
elif [[ $j = PA ]];then echo PA 5.5 4 >> new_pref.out;
elif [[ $j = PAA ]];then echo PAA 5.5 5 >> new_pref.out;
elif [[ $j = PAC ]];then echo PAC 3 4 >> new_pref.out;
elif [[ $j = PC ]];then echo PC 5 4.5 >> new_pref.out;
elif [[ $j = PCA ]];then echo PCA 5 3.5 >> new_pref.out;
elif [[ $j = PCC ]];then echo PCC 6.5 2.5 >> new_pref.out;
elif [[ $j = PE ]];then echo PE 8.5 8.5 >> new_pref.out;
elif [[ $j = Q ]];then echo Q 4.5 4 >> new_pref.out;
elif [[ $j = QA ]];then echo QA 6 3.5 >> new_pref.out;
elif [[ $j = QC ]];then echo QC 4.5 4 >> new_pref.out;
elif [[ $j = QE ]];then echo QE 3 5 >> new_pref.out;
elif [[ $j = QF ]];then echo QF 3 4.5 >> new_pref.out;
elif [[ $j = QG ]];then echo QG 3 2.5 >> new_pref.out;
elif [[ $j = QI ]];then echo QI 4.5 2.5 >> new_pref.out;
elif [[ $j = QK ]];then echo QK 7 4.5 >> new_pref.out;
elif [[ $j = S ]];then echo S 4.5 6.5 >> new_pref.out;
elif [[ $j = SC ]];then echo SC 4 7 >> new_pref.out;
elif [[ $j = SE ]];then echo SE 2 6.5 >> new_pref.out;
elif [[ $j = SEA ]];then echo SEA 4.5 6 >> new_pref.out;
elif [[ $j = SEC ]];then echo SEC 4.5 6 >> new_pref.out;
elif [[ $j = SN ]];then echo SN 5 7 >> new_pref.out;
elif [[ $j = SNA ]];then echo SNA 7 7 >> new_pref.out;
elif [[ $j = SNB ]];then echo SNB 6 6 >> new_pref.out;
elif [[ $j = SNC ]];then echo SNC 4.5 6 >> new_pref.out;
elif [[ $j = W ]];then echo W 5.5 4.5 >> new_pref.out;
elif [[ $j = WA ]];then echo WA 6.5 4.5 >> new_pref.out;
elif [[ $j = WAA ]];then echo WAA 7 5.5 >> new_pref.out;
elif [[ $j = WAC ]];then echo WAC 4.5 3 >> new_pref.out;
elif [[ $j = WAE ]];then echo WAE 4 3.5 >> new_pref.out;
elif [[ $j = WC ]];then echo WC 4 4 >> new_pref.out;
elif [[ $j = WCD ]];then echo WCD 5 5 >> new_pref.out;
elif [[ $j = WCG ]];then echo WCG 6.5 6.5 >> new_pref.out;
elif [[ $j = WCN ]];then echo WCN 4.5 5 >> new_pref.out;
elif [[ $j = WE ]];then echo WE 4 3.5 >> new_pref.out;
else
	echo $j not found >> new_pref.out;
fi;

done

