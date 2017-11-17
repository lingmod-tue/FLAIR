FLAIR localization readMe:

The strings-en files are localized strings for the English language, whereas the strings-de files are for german. The "general" strings are fully localized, in that we have strings for both languages.
The "constructions" files contain the strings for the various grammatical constructions used by FLAIR. For every construction, there are three rows: _gram-name_, _gram-path_ and _gram-helpText_
_gram-name_ - This is the name of the grammatical construction that's displayed to the user. It usually follows its default placeholder string (e.g: CLAUSE_DASS - Dass Clause)
_gram-path_ - This is the path string described by the construction's categories. (e.g: Direct questions has a path string of "Questions > Direct Questions")
_gram-helpText_ - This is the example text displayed to the user. It's formatted in the following manner: e.g., <insert example word(s) here>

The different string tables stored as separate spreadsheets in this file and are accessible from the bottom toolbar.
Each string table has three columns. The first and second columns are internal tags used by the webapp and provide context as to what the translatable string refers to and/or where it's used in the UI.
The third column is the actual localized string that needs translation.

Generally, grammatical constructions need localized strings for every language that uses them. If a construction is exclusive to just one language, its strings do not need to be localized to other languages. 
The language of construction in the interface depends on the language of the current operation and not on the actual display language.    

Localized strings are allowed to have inline references (no deeper than one level) to other strings with the following syntax: ${provider.tag}