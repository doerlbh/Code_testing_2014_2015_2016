
#!/usr/local/bin/perl

# Baihan Lin, Oct 2016
#
# A perl script to generate an lpsolve-format IP (integer program) 

$max=12;

## print out objective function
print "min:";
	for($i=1; $i<$max+1; $i++) {
		print "+y_",$i;
	}
print ";\n";

## constraint 1
for($j=1; $j<$max+1; $j++) {
	for($i=1; $i<$max+1; $i++) {
		print "+x_",$j,"_",$i;
	}
	print "=1;\n"
}

## constraint 2
for($j=1; $j<$max+1; $j++) {
	for($i=1; $i<$max+1; $i++) {
		print "x_",$j,"_",$i," -", "y_",$i,"<=0;\n";
	}
}

## constraint 3
for($j=1; $j<$max+1; $j++) {
	for($i=1; $i<$max+1; $i++) {

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
			for($k=1; $k<$max+1; $k++) {
				print "x_",$j,"_",$k," +", "x_",$i,"_",$k,"<=1;\n";
			}
		}
	}
}

## constraint 4
## specify that all variables are binary
print "bin ";
for($i=1; $i<$max+1; $i++) {
	if ($i>1) { print ","; } ## prepend a comma after the first variable
	print "+y_",$i;
}
for($j=1; $j<$max+1; $j++) {
	print ",";
	for($i=1; $i<$max+1; $i++) {
		print "x_",$j,"_",$i;
	}
}
print ";\n";