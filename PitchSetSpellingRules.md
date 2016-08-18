# Eighth-step resolution pitch set spelling rules

## General definition

Rules can be applied at the [**node-**](#node-level), [**edge-**](#edge-level), or [**graph-**](#graph-level)level:

- **Node-level rules**:
  - Applied to single `SpelledPitchClass` values
  - e.g., "avoid double flats/sharps"
- **Edge-level rules**:
  - Applied to all pairs `SpelledPitchClass` values in the graph
  - e.g., "avoid augmented unisons"
- **Graph-level rules**:
  - Applied to entire graph
  - Does not apply "double jeopardy" — penalizes only once if there are one or more instances of rule-breaking
  - e.g., "avoid eighth tone arrow direction conflicts"
  
Rules return a cost value, [0,1], which is later scaled by the program to allow rule weighting and user-controlled preferences.

---

<a id="node-level"></a>
## Node-level rules

The following rules are applied to single `SpelledPitchClass` values, without consideration of the surrounding harmonic or melodic context.

> These rules will most probably have user-definable weighting (`prefer no double-sharps`, etc.).

**Signature:** **`(A: SpelledPitchClass) -> Float`**

<a id="double-sharps-double-flats"></a>
### R<sub>n,1</sub>: Double sharps / double flats
* `1` if **A<sub>quarterStep</sub>** is `double-flat` or `double-sharp`
* `0` otherwise

<a id="bad-enharmonics"></a>
### R<sub>n,2</sub>: Bad enharmonics
* `1` if (**A<sub>letterName</sub>**, **A<sub>quarterStep</sub>**) = `(b, sharp)` or `(c, flat)` or `(e, sharp)` or `(f, flat)`
* `0` otherwise

> Consider merging with [**R<sub>n1</sub>: Double sharps / double flats**](#double-sharps-double-flats)

<a id="combining-quarter-tones-eighth-tones"></a>
### R<sub>n,3</sub>: Combining quarter tones and eighth tones
* `1` if **A<sub>quarterStep resolution</sub>** `== 0.5` and **A<sub>eighthStep</sub>** `!= 0`
* `0` otherwise

> In the case of an eighth-tone resolution spelling, prefer half-step accidental body values over quarter-step accidental body values. 

<a id="three-quarter-steps"></a>
### R<sub>n,4</sub>: Three-quarter-step values
* `1` if **A<sub>quarterStep</sub>** is `three-quarter-flat` or `three-quarter-sharp`
* `0` otherwise

<a id="edge-level"></a>
## Edge-level rules

The following rules are applied to dyads of `SpelledPitchClass` values.

**Signature:** **`(A: SpelledPitchClass) -> (B: SpelledPitchClass) -> Float`**

<a id="unisons"></a>
### R<sub>e,1</sub>: Unisons
* `1` if **A<sub>letterName</sub>** == **B<sub>letterName</sub>**
* `0` otherwise

> All unisons are augmented or diminished since we use a set of unique elements going into the problem.

<a id="crossovers"></a>
### R<sub>e,2</sub>: Crossovers 
* `1` if ?
* `0` otherwise

> e.g., (c, flat) and (b, sharp) for instance

_not sure how to implement this one, but I think you have to include checks on the quarter-tone directions_

<a id="augmented-diminished"></a>
### R<sub>e,3</sub>: Augmented / diminished intervals
* `0.2 * Interval(A,B).quality.degree`
* `0` if `Interval(A,B).quality` is not `augmented` or `diminished`

<a id="graph-level"></a>
## Graph-level rules

The following rules are applied to the entire graph, in its current state of construction.

**Signature:** **`(G: [SpelledPitchClass]) -> Float`**

<a id="quarter-step-incompatibility"></a>
### R<sub>g,1</sub>: Conflicting quarter-step directionality
* `1` if for any `A, B in S`, sign(**A<sub>quarterStep</sub>**) * sign(**B<sub>quarterStep</sub>**) = -1
* `0` otherwise

> Avoid `sharp` / `flat` direction mixes

<a id="eighth-step-incompatibility"></a>
### R<sub>g,2</sub>: Conflicting eighth-step directionality
* `1` if for any `A, B in S`, sign(**A<sub>eighthStep</sub>**) * sign(**B<sub>eighthStep</sub>**) = -1
* `0` otherwise

> Avoid `up` / `down` direction mixes