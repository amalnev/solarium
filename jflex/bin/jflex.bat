REM Please adjust JFLEX_HOME to suit your needs
REM (please do not add a trailing backslash)

set JFLEX_VERSION=1.7.0
echo "%JFLEX_HOME%"

java -Xmx128m -jar "%JFLEX_HOME%"\lib\jflex-full-%JFLEX_VERSION%.jar %*
