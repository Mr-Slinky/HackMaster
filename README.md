# Terminal-Takedown

## Description
A replication of the Fallout 3 / 4 hacking minigame using JavaFX

##Javadoc:

![API Documentation](https://mr-slinky.github.io/HackMaster/docs/com.slinky.hackmaster/module-summary.html)

## Understanding the Code – Backend Architecture

### Cells and Clusters

To grasp the foundational aspects of the code, it's essential to begin with the core interfaces: `Cell` and `CellCluster`. These interfaces, especially `Cell`, serve as the fundamental building blocks of the program's interactive components.

The `Cell` interface is implemented by the `AbstractCell` class, which offers a default implementation for behaviours common to all `Cell` types. Two concrete classes, `LetterCell` and `SymbolCell`, extend `AbstractCell` by adding specific behaviours to manage letters and symbols, respectively.

Similarly, the `CellCluster` interface is implemented by the `AbstractCluster` class. `AbstractCluster` provides default behaviours for all `CellClusters`, such as retrieving the first and last `Cell` objects. The concrete implementations, `LetterCluster` and `SymbolCluster`, specialise in containing `LetterCell` and `SymbolCell` objects, respectively.

![Cell and CellCluster Class Diagram]( <!-- TODO -->)

### Cell Management and Clustering

The `CellController` interface defines the contract for classes responsible for managing and clustering `Cell` objects. Currently, the only concrete implementation of this interface is the `CellMaster` class.

The `CellMaster` class is constructed with a `SymbolClusterStrategy`, provided through dependency injection. This strategy defines the rules for clustering symbols into `SymbolCluster` objects. A similar strategy is not required for letters due to the straightforward nature of clustering letters.