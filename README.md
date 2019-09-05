# FLAIR (Form-Focused Linguistically Aware Information Retrieval)
#### (Now with automatic question generation!)

FLAIR is an online tool for language teachers and learners that:
* searches the web for a topic of interest
* analyzes the results for grammatical constructions and readability levels
* re-ranks the results according to your (pedagogical or learning) needs specified in the settings

This fork of FLAIR additionally supports the automatic generation of factual (reading comprehension) questions from the contents of the results.

Built using the [Google Web Toolkit](http://www.gwtproject.org/),  [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/), and [Microsoft Bing](https://www.bing.com/). 

# Usage

The project can be built using [Apache Maven](https://maven.apache.org/) and deployed to any fairly recent Java EE web server such as [Apache Tomcat](https://tomcat.apache.org/).

The web search functionality relies on the Microsoft Bing API and consequently requires an API key that must be specified in the `src/main/resources/com/flair/server/crawler/BingSearchAgent.properties` resource file, like so:

```
# Secret API Key
api-key=<api_key_goes_here>
```

# Relevant Citations

```
@article{chinkina2016online,
  title={Online information retrieval for language learning},
  author={Chinkina, Maria and Kannan, Madeeswaran and Meurers, Detmar},
  journal={Proceedings of ACL-2016 System Demonstrations},
  pages={7--12},
  year={2016}
}

@article{heilman2011automatic,
  title={Automatic factual question generation from text},
  author={Heilman, Michael},
  year={2011}
}
```
