Splay Tree and Weighted Balanced Tree
jirvine (Jamie Irvine)

Description
-----------
This repo contains the implementations of a splay tree and a weighted balanced
tree. It also contains a testing harness for correctness and a runtime analyzer.

The Trees
---------
Splay trees restructure themselves to bring popular queried items nearer
to the top of the tree, to speed up future repetitive queries. It does this by
using the zig, zig-zig, and zig-zag operations. It is designed for dynamic
querying patterns, but also performs well on static, known patterns.

Weighted balanced trees leverage a known static query patterns to construct the
best balanced tree. The weight of each node are the probabilities of each
element beign accessed. It is structured such that for each node, the total weight
below and including its left child is as close to the total weight below and
including its right child. It is implemented in time O(n log n).

Runtime Analyzer
----------------
The runtime analyzer (RuntimeAnalyzer.java) is designed to test the runtimes on
a statically defined query pattern. The performance of the splay tree and the
weighted balanced tree can be compared.
