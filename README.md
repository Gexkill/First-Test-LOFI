#XML

```xml
<?xml version="1.0"?>
<!DOCTYPE WCC[ 
  <!ELEMENT WCC (Edition+)>
  <!ELEMENT Edition (Date, Location, Competitor+)>
  <!ELEMENT Competitor (Name, Cocktail+)>
  <!ELEMENT Cocktail (Name, Category, Ingredient+, 
    OriginalityPoints, AppearancePoints, 
    TastePoints, OverallScore)>
]>
<WCC>
  <edition>
    <date>01/01/2000</date>
    <location></location>
    <competitor>
      <Name>Ginetto</Name>
      <Cocktail>
        <Name>Cocktail1</Name>
        <Category>Casino</Category>
        <Ingredient>Sambuca</Ingredient>
        <Ingredient>Gin</Ingredient>
        <Ingredient>Prosecco</Ingredient>
        <OriginalityPoints></OriginalityPoints>
        <AppearancePoints>50</AppearancePoints>
        <TastePoints></TastePoints>
        <OverallScore>51</OverallScore>
      </Cocktail>
    </competitor>
    <competitor>
      <Name>Mario</Name>
      <Cocktail>
        <Name>Cocktail2</Name>
        <Category>Clover Club</Category>
        <Ingredient>Gin</Ingredient>
        <Ingredient>Sambuca</Ingredient>
        <Ingredient>Acqua</Ingredient>
        <OriginalityPoints></OriginalityPoints>
        <AppearancePoints>9</AppearancePoints>
        <TastePoints></TastePoints>
        <OverallScore>52</OverallScore>
      </Cocktail>
    </competitor>
  </edition>
  <edition>
    <date>01/01/2001</date>
    <location></location>
    <competitor>
      <Name>Pinuzzo</Name>
      <Cocktail>
        <Name>Cocktail3</Name>
        <Category>Angel Face</Category>
        <Ingredient>Aperol</Ingredient>
        <Ingredient>Gin</Ingredient>
        <Ingredient>Champagne</Ingredient>
        <OriginalityPoints></OriginalityPoints>
        <AppearancePoints>90</AppearancePoints>
        <TastePoints></TastePoints>
        <OverallScore>53</OverallScore>
      </Cocktail>
    </competitor>
    <competitor>
      <Name>Pinuzzo</Name>
      <Cocktail>
        <Name>Cocktail4</Name>
        <Category>Americano</Category>
        <Ingredient>Jack Daniels</Ingredient>
        <Ingredient>Coca Cola</Ingredient>
        <Ingredient>Gin</Ingredient>
        <OriginalityPoints></OriginalityPoints>
        <AppearancePoints>14</AppearancePoints>
        <TastePoints></TastePoints>
        <OverallScore>54</OverallScore>
      </Cocktail>
    </competitor>
  </edition>
  <edition>
    <date>01/01/2002</date>
    <location></location>
    <competitor></competitor>
    <competitor>
      <Name>Marcolino</Name>
      <Cocktail>
        <Name>Cocktail5</Name>
        <Category>Americano</Category>
        <Ingredient>Jaggermeister</Ingredient>
        <Ingredient>Glen Grant</Ingredient>
        <Ingredient>Assenzio</Ingredient>
        <OriginalityPoints></OriginalityPoints>
        <AppearancePoints>18</AppearancePoints>
        <TastePoints></TastePoints>
        <OverallScore>55</OverallScore>
      </Cocktail>
    </competitor>
  </edition>
  <edition>
    <date>01/01/2003</date>
    <location></location>
    <competitor></competitor>
    <competitor>
      <Name>Ginetto</Name>
      <Cocktail>
        <Name>Cocktail6</Name>
        <Category>Daiquiri</Category>
        <Ingredient>Red Bull</Ingredient>
        <Ingredient>Jaggermeister</Ingredient>
        <Ingredient>Gin</Ingredient>
        <OriginalityPoints></OriginalityPoints>
        <AppearancePoints>50</AppearancePoints>
        <TastePoints></TastePoints>
        <OverallScore>56</OverallScore>
      </Cocktail>
    </competitor>
  </edition>
  <edition>
    <date>01/01/2004</date>
    <location></location>
    <competitor>
      <Name>Pinuzzo</Name>
      <Cocktail>
        <Name>Cocktail7</Name>
        <Category>Angel Face</Category>
        <Ingredient>Gin</Ingredient>
        <Ingredient>Arancia</Ingredient>
        <Ingredient>Aperol</Ingredient>
        <OriginalityPoints></OriginalityPoints>
        <AppearancePoints>43</AppearancePoints>
        <TastePoints></TastePoints>
        <OverallScore>57</OverallScore>
      </Cocktail>
    </competitor>
    <competitor>
      <Name>Giuseppe</Name>
      <Cocktail>
        <Name>Cocktail8</Name>
        <Category>Daiquiri</Category>
        <Ingredient>Gin</Ingredient>
        <Ingredient>Sambuca</Ingredient>
        <Ingredient>Prosecco</Ingredient>
        <OriginalityPoints></OriginalityPoints>
        <AppearancePoints>43</AppearancePoints>
        <TastePoints></TastePoints>
        <OverallScore>58</OverallScore>
      </Cocktail>
    </competitor>
  </edition>
</WCC>
```

The DTD above (in which unspecified elements only contain PCData) describes the results of the World Cocktail Championship, organized by the International Bartender Association, where competitors present originally conceived cocktails, and a professional jury grades their creations w.r.t. their originality, appearance, and taste. Extract in XQuery:

1. The name of the creator of the *''ugliest''* cocktail ever presented in the *''Daiquiri''* category (*w.r.t. the appearance score*).

```xquery
let $ugliestScore := min(
  for $s in doc("wcc.xml")//Cocktail
  where $s/Category = "Daiquiri"
  return $s/AppearancePoints/text()
)
for $s in doc("wcc.xml")//Cocktail
where $s/AppearancePoints = $ugliestScore and $s/Category = "Daiquiri"
return <ugliest-cocktail>
        <Name>{$s/Name/text()}</Name>
        <cocktailScore>{$ugliestScore}</cocktailScore>
       </ugliest-cocktail>
```
2. For each competitor, their best creation (*w.r.t. the overall score*) for each category in which they have competed.

```xquery
for $compName in distinct-values(doc("wcc.xml")//competitor/Name/text())
(:
let $categoryByCompetitor := distinct-values(
  for $comp in doc("wcc.xml")//competitor
  where $comp/Name = $compName
  return $comp/Cocktail/Category/text()
)
:)
let $categoryByCompetitor := distinct-values( doc("wcc.xml")//competitor[./Name = $compName]/Cocktail/Category )

return <Competitor>
         <Name>{$compName}</Name>
         {
           for $eachCat in $categoryByCompetitor
           let $scoreForEachCategoryByCompetitor := max(
             for $scoreValue in doc("wcc.xml")//competitor
             where $scoreValue/Cocktail/Category = $eachCat
               and $scoreValue/Name = $compName
             return $scoreValue/Cocktail/OverallScore/text()
           ) 
           return <Category>
                    <Name>{$eachCat}</Name>
                    <scoreAchieved>{$scoreForEachCategoryByCompetitor}</scoreAchieved>
                  </Category>
         }
       </Competitor>
```

3. The pairs of cocktails, if any, that, although belonging to different categories, are made with exactly the same ingredients (*indepedently of the listing order, and, of course, of the quantities, that are kept secret during the championship*).

```xquery
(:
declare function local:valueCompare
  ( $arg1 as xs:anyAtomicType* ,
    $arg2 as xs:anyAtomicType* )  as xs:anyAtomicType* {
      
      if (compare(string-join(sort(distinct-values($arg1))),
          string-join(sort(distinct-values($arg2))))) then (false()) else (true())
    };

for $cocktail in doc('wcc.xml')//Cocktail
let $ingr := $cocktail/Ingredient
let $PairsList := (
  for $temp in doc('wcc.xml')//Cocktail
  where $temp/Name/text() != $cocktail/Name/text()
  let $ingr2 := $temp/Ingredient
  return if (local:valueCompare($ingr/text(), $ingr2/text())) then ($temp/Name/text()) else ()
)

let $result := if (empty($PairsList)) then () else (
  for $pl in $PairsList
    return
      <pairs> 
        <name1>{$cocktail/Name/text()}</name1>
        <name2>{$pl}</name2>        
        {$ingr}
      </pairs>
)
return $result
:)

for $c1 in doc('wcc.xml')//Cocktail, $c2 in doc('wcc.xml')//Cocktail
where $c1/Category != $c2/Category 
  and (every $ing1 in $c1/Ingredient satisfies $ing1 = $c2/Ingredient) 
  and (every $ing2 in $c1/Ingredient satisfies $ing2 = $c1/Ingredient)
return <pair> { $c1/Name, $c2/Name } </pair>
```

