# Problems-solved-using-Graph-Algorithms
#Copyright Stan Andreea-Cristina 323CA

Beamdrone.java
        complexitatea temporală a algoritmului: T = O(M ∗ log N), unde N = nr de noduri
                                                                    M = nr. de muchii
        complexitatea spațială a algoritmului: S = O(N), unde N = nr. de noduri 

           * readInput() - Pentru o interpretare mai usoara a locurilor in care
                        sunt pereti si nu se poate trece, am ales sa notez locurile
                        libere cu numere incepand de la 0, iar locurile in care am
                        zid sa fie notate cu -100. Astfel, dupa ce citeam cate o
                        pozitie din input il codam in functie de nevoie in matricea
                        mea (track).

            * dijkstra() - Aici are loc toata logica algoritmului Dijkstra. Pentru
                        usurinta, am ales sa renunt la vectorul de parinti si la
                        matricea de adiacenta, intrucat folosirea lor mi s-a parut
                        redundanta. 
                        Dupa ce am initializat vectorul de distante cu infinit si
                        priority queue-ul folosit cu punctul de start din matrice
                        (track[x_i][y_i]), am inceput sa parcurg coada pana cand
                        aceasta devine goala(desi opresc algoritmul cand ajung pe
                        pozitia finala data in input - track[x_f][y_f]).
                        Pentru fiecare nod din coada calculez vecinii din nord, sud,
                        est si vest avand grija sa nu depasesc dimensiunile matricii.

                        In perechea pe care o adaug treptat in coada am retinut si 
                        coordonatele pozitiei curente, dar si directia din care am
                        ajuns in acea pozitie. Astfel, daca un vecin este valid
                        (nu este un perete sau nu iese din matrice) in functie de
                        directia din care am venit va fi adaugat in coada cu costul
                        curent, costul marit cu o unitate sau costul marit cu 2 unitati.

                        Repet algoritmul pana cand ajung la coordonatele x_f si y_f din
                        input.

Cuse.java - rezolvarea mea are la baza urmatorul link unde se realizeaza sortarea topologica :
            https://www.geeksforgeeks.org/java-program-for-topological-sorting/

        complexitatea temporala a algoritmului : T = O(V + E), unde V = vertices
                                                                E = edges
        complexitatea spatiala a algoritmului : S = O(V), unde V = vertices

            readInput() - Pentru eficienta, am ales sa imi creez si matricea de adiacenta
                        odata cu citirea datelor din fisier si memorarea lor in matrice.
                        Astfel, cu ajutorul unui vector bool ce verifica daca nodul a fost
                        adaugat sau nu, creez in functie de matricea de input matricea de 
                        adiacenta.

            getResult() - Functia se ocupa cu initializarea vectorului de vizitati folosit
                            in sortarea topologica si apelarea functiei ajutatoare recursiva
                            pentru fiecare nod nevizitat.

            getResultHelper() - Functia incepe prin a marca nodul curent ca vizitat, apoi se
                                parcurge matricea de adiacenta si pentru fiecare nod nevizitat
                                se apeleaza recursiv functia helper.
                                In final adaug in ArrayList fiecare nod parcurs si afisez in
                                fisier rezultatul.     
