# Semester Project A

This project is designed for analyzing networks represented as graphs. It provides comprehensive
functionality to perform various operations on graph data, including finding paths between nodes, identifying disjoint
sets of paths, and conducting analyzes on the network structure.

## Table of Contents

- [Overview](#overview)
- [Components](#components)
- [Example Components](#example-components)
- [Input Data Structure](#input-data-structure)
- [Dependencies](#dependencies)
- [Output](#output)

## Overview

This program represents a semester project for the *Data Structures and Algorithms* course offered at the
*University of Pardubice*. It serves as a practical application of graph theory and algorithm analysis within the
context of railway network.

## Components

### Package `graph.model`

- **Abstract Class `Graph`**: Represents the graph structure with vertices and edges.
- **Abstract Class `Vertex`**: Represents a vertex in the graph with an id, type, and neighbors.
- **Abstract Class `Path`**: Represents a path in the graph consisting of vertices.
- **Abstract Class `DisjointSet`**: Represents disjoint sets of paths in the graph.
- **Enum `VertexType`**: Defines type of vertex.

### Package `graph.calculation`

- **Abstract Class `PathFinder`**: Finds paths in the graph using depth-first search.
- **Abstract Class `DisjointPathsFinder`**: Finds disjoint sets of paths in the graph.

## Example Components

### Package `example.railway.model`

- **Class `Railway`**: Represents the railway graph. Extends the `Graph`.
- **Class `RailwaySwitch`**: Represents a railway switch as a vertex in the railway graph. Extends the `Vertex`.
- **Class `Route`**: Represents a route in the railway graph. Extends the `Path`.
- **Class `DisjointRoutes`**: Represents disjoint routes in the railway graph. Extends the `DisjointSet`.

### Package `example.railway.calculation`

- **Class `RouteFinder`**: Finds routes in the railway graph. Extends the `PathFinder`.
- **Class `DisjointRoutesFinder`**: Finds disjoint routes in the railway graph. Extends the `DisjointPathsFinder`.

### Package `example.railway.utils`

- **Class `RailwayUtils`**: Provides utility functions to parse a railway graph from a file and to print results
  provided by `PathFinder` and `DisjointPathsFinder`.

## Input Data Structure

The input data file should be JSON. The JSON input data should be structured as an array of objects, where each object
represents a vertex in the graph. Each vertex object contains the following attributes:

- **`ID` (Integer)**: Unique identifier for the vertex.
- **`TYPE` (String)**: Optional. Indicates type of the vertex, which can be one of the following (if not specified, it
  fallbacks to type `V`):
    - `V`: Common vertex.
    - `S`: Start vertex.
    - `E`: End vertex.
    - `SE`: Start and End vertex.
    - `C`: Crossroad vertex.
- **`NEIGHBORS` (Array of Integers)**: Optional. List of IDs representing the neighboring vertices connected to the
  current vertex.
    - For a vertex of type `C`, the `NEIGHBORS` array should consist of pairs that indicate *from* which vertex and *to*
      which vertex it is possible to get through the crossroad.

### Crossroad Example

- `ID: 5, TYPE: "C", NEIGHBORS: [3, 4, 2, 4, 3, 6]`
- In this example paths will get through the crossroad like this: `[3, 5, 4] [3, 5, 6] [2, 5, 4]`, where `[2, 3]`
  are **input** vertices, `5` is the **crossroad**, and `[4, 6]`are **output** vertices.

## Dependencies

- **Gson**: Library for parsing JSON input data.

Ensure these dependencies are installed before running the program.

# Output

This project generates output files in text format containing information about the vertices, routes, and disjoint
routes. The output files are stored in the `out/[name-of-the-task]/`directory and contain human-readable representations
of the data. Each class has its own text file:

- `vertices.txt`: Contains information about the vertices in the railway graph.
- `routes.txt`: Contains information about the routes found in the railway graph.
- `disjoint-routes.txt`: Contains information about the disjoint routes found in the railway graph.

The text files are created by serializing the data using the `toString()` method of each class and writing the results
to the corresponding text files.
