# LanguageDetection
Language Detection for multilingual websites

Language - Java Language Detection Jar - https://github.com/optimaize/language-detector Parser - JSoup, HTTP Libraries Crawl Depth - 2

#Project Components

![#f03c15](https://placehold.it/15/f03c15/000000?text=+) `CoreParserInvoker.java` - Contains the Core Crawler Logic , Recursive Parsing Based on Crawler Depth

![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `HTTPCore.java` - Root URL undergoes ping test before crawling

![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `GraphConnection.java` - Still underdevelopment

![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `DataBlobber.java` - Container for the Printing the end result

![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `RedirectResolver.java` - Resolves the redirect urls - infinite loops

![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `Language.java` - ENUM - Language Support for 54 profiles

![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `LinkExtractor.java` - HREF Extractor

![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `LanguageCodeDetection.java` - Core logic - Returns Dominant Language of a WebPage

![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `ScreenshotService.java` - Takes the screenshot of the website (POC ) - Computer Vision model building for Language Detection
