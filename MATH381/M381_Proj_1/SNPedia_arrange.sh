#!/usr/local/bin/
# Baihan Lin
# July 2016

#cat rslist | uniq -d > rslistuniq;

now=$(date +"%Y%m%d");

ls *_vir > vir_list_$now

for k in `cat vir_list*`; do

	echo -----------------------
	echo $k starts. ------------
	for j in `cat $k`; do
		python SNPedia_scrape.py $j;
		#echo $j-out.txt;
		
		grep ">PMID"  $j-out.txt > $j-PMID;
		sed -i '' 's/.*PMID/PMID/g' $j-PMID;
		sed -i '' "s#</a>] #$(printf '\t')#g" $j-PMID;
		sed -i '' "s#</a><a href=\"/index.php/File:OA-icon.png\" class=\"image\"><img alt=\"OA-icon.png\" src=\"https://media.snpedia.com/images/5/5b/OA-icon.png\" width=\"15\" height=\"15\" />#$(printf '\t')#g" $j-PMID;
		sed -i '' "s#</a>]#$(printf '\t')#g" $j-PMID;
		sed -i -e "s/^/$j$(printf '\t')/" $j-PMID;
		sed -i -e "s/$(printf '\t')$(printf '\t')/$(printf '\t')/" $j-PMID;
		rm $j-out.txt;
		
		#echo $j is good.
		
	done

	cat Rs* >> tempPMIDlist$now;
	sort tempPMIDlist$now > tempPMID$now.sort;
	uniq -d tempPMID$now.sort > tempPMID$now.uniq;
	grep "Rs"  tempPMID$now.uniq > ${k//vir/PMID}_$now.uniq;
	rm temp*;
	rm Rs*;

	echo $k is finished.~~~~~~~~

done

#cat *PMID.combo >> PMIDlist$now;



