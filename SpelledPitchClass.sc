
	// string conversion for letter names
	*asString_ln {
		arg letterName;
		^switch(letterName)
		{0} {"C"}
		{1} {"D"}
		{2} {"E"}
		{3} {"F"}
		{4} {"G"}
		{5} {"A"}
		{6} {"B"}
		{Error("invalid letter name: %".format(letterName)).throw};
	}

	// string conversion for quarter tones
	*asString_qt {
		arg quarterTone;
		^switch(quarterTone)
		{-4} {"bb"}
		{-3} {"db"}
		{-2} {"b"}
		{-1} {"d"}
		{0} {""}
		{1} {"t"}
		{2} {"#"}
		{3} {"#t"}
		{4} {"x"}
		{Error("invalid quarter tone: %".format(quarterTone)).throw};
	}

	// string conversion for eighth tones
	*asString_et {
		arg eighthTone;
		^switch(eighthTone)
		{-1} {"dn"}
		{0} {""}
		{1} {"up"}
		{Error("invalid eighth tone: %".format(eighthTone)).throw};
	}
