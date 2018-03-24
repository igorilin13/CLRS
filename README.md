# CLRS In Java
Java implementations from CLRS "Introduction to Algorithms", 3rd edition.

## Created With

* [IntelliJ IDEA](https://www.jetbrains.com/idea/)

## Reference

#### Chapter 2: Getting Started
```bash
* Insertion sort: #main.arrays.SortingAlgorithms.insertionSort()
* Merge sort:     #main.arrays.SortingAlgorithms.mergeSort()
* Bubblesort:     #main.arrays.SortingAlgorithms.bubbleSort()
```

#### Chapter 4: Divide-and-Conquer
```bash
* Maximum-subarray problem:     #main.arrays.ArrayAlgorithms.maxSubarray()
* Square matrix multiplication: #main.arrays.ArrayAlgorithms.squareMatrixMultiply()
```

#### Chapter 6: Heapsort
```bash
* Binary Heap (min/max):    #main.datastructures.BinaryHeap
* Heapsort:                 #main.arrays.SortingAlgorithms.heapSort()
* Priority queue (min/max): #main.datastructures.PriorityQueue
```

#### Chapter 7: Quicksort
```bash
* Quicksort:            #main.arrays.SortingAlgorithms.quickSort()
* Randomized quicksort: #main.arrays.SortingAlgorithms.randomizedQuickSort()
```

#### Chapter 8: Sorting in Linear Time
```bash
* Counting sort: #main.arrays.SortingAlgorithms.countingSort()
* Radix sort:    #main.arrays.SortingAlgorithms.radixSort()
* Bucket sort:   #main.arrays.SortingAlgorithms.bucketSort()
```

#### Chapter 9: Medians and Order Statistics
```bash
* Randomized select: #main.arrays.ArrayAlgorithms.randomizedSelect()
```

#### Chapter 10: Elementary Data Structures
```bash
* Stack:       #main.datastructures.Stack
* Queue:       #main.datastructures.Queue
* Linked list: #main.datastructures.LinkedList
```

#### Chapter 11: Hash Tables
```bash
* Direct-address table:     #main.datastructures.hash.DirectAddressTable
* Hash table with chaining: #main.datastructures.hash.ChainedHashTable
* Open addressing:          #main.datastructures.hash.OpenAddressTable
```

#### Chapter 12: Binary Search Trees
```bash
* In order walk:      #main.datastructures.tree.BinarySearchTree.inOrderWalk()
* Search (recursive): #main.datastructures.tree.BinarySearchTree.recursiveSearch()
* Search (iterative): #main.datastructures.tree.BinarySearchTree.iterativeSearch()
* Minimum:            #main.datastructures.tree.BinarySearchTree.minNode()
* Maximum:            #main.datastructures.tree.BinarySearchTree.maxNode()
* Successor:          #main.datastructures.tree.BinarySearchTree.successorNode()
* Precessor:          #main.datastructures.tree.BinarySearchTree.predecessorNode()
* Insertion:          #main.datastructures.tree.BinarySearchTree.insert()
* Deletion:           #main.datastructures.tree.BinarySearchTree.delete()
```

#### Chapter 13: Red-Black Trees
```bash
* Insertion:  #main.datastructures.tree.RedBlackTree.insertFixup()
* Deletion:   #main.datastructures.tree.RedBlackTree.delete()
```

#### Chapter 14: Augmenting Data Structures
```bash
* Select: #main.datastructures.tree.OrderedStatisticsTree.select()
* Rank:   #main.datastructures.tree.OrderedStatisticsTree.calcRank()
* Insert: #main.datastructures.tree.OrderedStatisticsTree.insertFixup()
* Delete: #main.datastructures.tree.OrderedStatisticsTree.delete()
```

#### Chapter 15: Dynamic Programming
```bash
* Rod cutting (top-down recursive): #main.dynamic.RodCutting
* Matrix chain order:               #main.dynamic.MatrixChainMultiplication
* Longest common subsequence:       #main.dynamic.LongestCommonSubsequence
* Optimal binary search tree:       #main.dynamic.OptimalBinarySearchTree
```

#### Chapter 16: Greedy Algorithms
```bash
* Activity-selection problem: #main.greedy.ActivitySelector
* Huffman codes:              #main.greedy.HuffmanCoding
```

#### Chapter 17: Amortized Analysis
```bash
* Multipop:       #main.datastructures.Stack.multiPop
* Binary counter: #main.amortized.BinaryCounter
```

#### Chapter 18: B-Trees
```bash
* Search:        #main.datastructures.advanced.BTree.search()
* Insert:        #main.datastructures.advanced.BTree.insert()
* Delete:        #main.datastructures.advanced.BTree.delete()
* In order walk: #main.datastructures.advanced.BTree.inOrderWalk()
```

#### Chapter 19: Fibonacci Heaps
```bash
* Insert:       #main.datastructures.advanced.FibonacciMinHeap.insert()
* Delete:       #main.datastructures.advanced.FibonacciMinHeap.delete()
* Union:        #main.datastructures.advanced.FibonacciMinHeap.union()
* Extract min:  #main.datastructures.advanced.FibonacciMinHeap.extractMin()
* Decrease key: #main.datastructures.advanced.FibonacciMinHeap.decreaseKey()
```

#### Chapter 20: van Emde Boas Trees
```bash
* Member:      #main.datastructures.advanced.VanEmdeBoasTree.contains()
* Minimum:     #main.datastructures.advanced.VanEmdeBoasTree.min()
* Maximum:     #main.datastructures.advanced.VanEmdeBoasTree.max()
* Predecessor: #main.datastructures.advanced.VanEmdeBoasTree.predecessor()
* Successor:   #main.datastructures.advanced.VanEmdeBoasTree.successor()
* Insert:      #main.datastructures.advanced.VanEmdeBoasTree.insert()
* Delete:      #main.datastructures.advanced.VanEmdeBoasTree.delete()
```

#### Chapter 21: Data Structures for Disjoint Sets
```bash
* Connected components: #test.datastructures.advanced.DisjointSetForestTest.testConnectedComponents()
* Disjoint-set forest:  #main.datastructures.advanced.DisjointSetForest
```

#### Chapter 22: Elementary Graph Algorithms
```bash
* Representation:       #main.graph.Graph
* Breadth-first search: #main.graph.algorithms.elementary.BreadthFirstSearch
* Depth-first search:   #main.graph.algorithms.elementary.DepthFirstSearch
* Topological sort:     #main.graph.algorithms.elementary.TopologicalSort
```

#### Chapter 23: Minimum Spanning Trees
```bash
* Kruskal algorithm: #main.graph.algorithms.spanning.KruskalMinSpanningTree
* Prim algorithm:    #main.graph.algorithms.spanning.PrimMinSpanningTree
```

#### Chapter 24: Single-Source Shortest Paths
```bash
* Bellman-Ford algorithm: #main.graph.algorithms.shortest.BellmanFordShortestPath
* DAG shortest path:      #main.graph.algorithms.shortest.DAGShortestPath
* Dijkstra algorithm:     #main.graph.algorithms.shortest.DijkstraShortestPath
```

#### Chapter 25: All-Pairs Shortest Paths
```bash
* Matrix:                   #main.graph.algorithms.shortest.all.MatrixShortestPaths
* Floyd-Warshall algorithm: #main.graph.algorithms.shortest.all.FloydWarshallShortestPaths
* Transitive closure:       #main.graph.algorithms.shortest.all.TransitiveClosure
* Johnson’s algorithm:      #main.graph.algorithms.shortest.all.JohnsonAllShortestPaths
```

#### Chapter 26: Maximum Flow
```bash
* Edmonds-Karp algorithm: #main.graph.algorithms.maxflow.EdmondsKarpMaxFlow
```

#### Chapter 27: Multithreaded Algorithms
```bash
* Fibonacci numbers:            #main.parallel.FibNumbers
* Matrix-vector multiplication: #main.parallel.MatrixVectorMultiply
* Matrix multiplication:        #main.parallel.SquareMatrixMultiply
* Binary search:                #main.arrays.ArrayAlgorithms.binarySearch()
```

#### Chapter 28: Matrix Operations
```bash
* Linear equations:   #main.matrix.LinearEquations
* Inverting matrices: #main.matrix.InverseMatrix
```

#### Chapter 29: Linear Programming
```bash
* Simplex algorithm: #main.linear.SimplexAlgorithm
```

#### Chapter 30: Polynomials and the FFT
```bash
* Plus/minus, multiplcation: #main.polynomial.Polynomial
```

#### Chapter 31: Number-Theoretic Algorithms
```bash
* Euclid algorithm:            #main.number.NumberAlgorithms.euclid()
* Euclid algorithm (extended): #main.number.NumberAlgorithms.extEuclid()
* Modular linear equation:     #main.number.NumberAlgorithms.modularSolve(	)
* Modular exponentiation:      #main.number.NumberAlgorithms.modularExp()
* Pseudoprime:                 #main.number.NumberAlgorithms.isPseudoPrime()
* Miller-Rabin prime:          #main.number.NumberAlgorithms.isMillerRabinPrime()
* Pollard-Rho heuristic:       #main.number.NumberAlgorithms.pollardRhoFactor()
```

#### Chapter 32: String Matching
```bash
* Naive:                        #main.string.StringMatching.naiveMatch()
* Rabin-Karp algorithm:         #main.string.StringMatching.rabinKarpMatch()
* Finite automata:              #main.string.StringMatching.finiteAutomationMatch()
* Knuth-Morris-Pratt algorithm: #main.string.StringMatching.kmpMatch()
```

#### Chapter 33: Computational Geometry
```bash
* 2-segments (vectors) direction:           #main.geometry.Vector.directionFrom()
* Angle turn direction:                     #main.geometry.GeometryUtils.angleDirection()
* 2-segments (vectors) intersection:        #main.geometry.Vector.intersects()
* Any-pair segments (vectors) intersection: #main.geometry.VectorsIntersection.anyIntersect()
* Graham scan convex hull:                  #main.geometry.GrahamScanConvexHull
```
#### Chapter 35: Approximation Algorithms
```bash
* Vertex-cover problem:                        #main.approximation.VertexCoverAlgorithm
* Traveling-salesman problem:                  #main.approximation.TravelingSalesmanAlgorithm
* Set-covering problem:                        #main.approximation.SetCoverAlgorithm
* Randomized MAX-3-CNF satisfiability problem: #main.approximation.Max3CnfSatAlgorithm
* Weighted vertex-cover problem:               #main.approximation.WeightedVertexCoverAlgorithm
* Subset-sum problem:                          #main.approximation.SubsetSumAlgorithm
```
