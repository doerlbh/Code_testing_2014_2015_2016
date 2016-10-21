
#!/usr/local/bin/perl

# Baihan Lin, Oct 2016
#
# A perl script to generate an lpsolve-format IP (integer program) 

$max=12;

## print out objective function
print "min:";
	for($i=0; $i<$max; $i++) {
		print "+y_",$i;
	}
print ";\n";

## constraint 1
for($j=0; $j<$max; $j++) {
	for($i=0; $i<$max; $i++) {
		print "+x_",$j,"_",$i;
	}
	print "=1;\n"
}

## constraint 2
for($j=0; $j<$max; $j++) {
	for($i=0; $i<$max; $i++) {
		print "x_",$j,"_",$i," -", "y_",$i,"<=0;\n";
	}
}

## constraint 3
for($j=0; $j<$max; $j++) {
	for($i=0; $i<$max; $i++) {

		# prime number check
		$n=$j^2+$i^2+$i*$j+4;
		$d=0; # 0 is prime, 1 is not
		for($c=2;$c<=$n-1;$c++) {
			if($n%$c==0) {
				$d=1;
				break;
			}
		}
		if($d==0){
			for($k=0; $k<$max; $k++) {
				print "x_",$j,"_",$k," +", "x_",$i,"_",$k,"<=1;\n";
			}
		}
	}
}

## specify that all variables are binary
print "bin ";
for($i=0; $i<$max; $i++) {
	if ($i>0) { print ","; } 
	print "+y_",$i;
}
for($j=0; $j<$max; $j++) {
	for($i=0; $i<$max; $i++) {
	if ($i+$j>0) { print ","; } ## prepend a comma after the first variable
		print "x_",$j,"_",$i;
	}
}
print ";\n";