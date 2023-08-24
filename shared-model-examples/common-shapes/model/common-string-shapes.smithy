$version: "2.0"

namespace example.common.strings

@pattern("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")
@length(min: 7, max: 16)
string IpV4Address

@pattern("^[ a-zA-Z0-9_:-]{1,256}$")
@length(min: 1, max: 256)
string AlphaNumericName
