import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm
import math

def calculate_and_plot_probability(a, b, mean, std_dev):
    # Calcola la probabilità Pr(a <= X <= b)
    probability = norm.cdf(b, loc=mean, scale=std_dev) - norm.cdf(a, loc=mean, scale=std_dev)

    # Calcola la funzione di densità di probabilità (PDF)
    x_values = np.linspace(mean - 3 * std_dev, mean + 3 * std_dev, 1000)
    y_values = norm.pdf(x_values, loc=mean, scale=std_dev)
    
    # Stampa la probabilità Pr(a <= X <= b)
    print(f"La probabilità Pr({a} <= X <= {b}) è del: {100*probability:.2f} %")
    
    # Plot dell'intera distribuzione normale
    plt.plot(x_values, y_values, label=f'μ={mean}, σ={std_dev:.2f}')
    plt.fill_between(x_values, y_values, where=[(a <= x <= b) for x in x_values],color='red', alpha=0.4, label='Area di Interesse')
    plt.title('Distribuzione Normale Gaussiana e Probabilità')
    plt.xlabel('X')
    plt.ylabel('Densità di Probabilità')
    plt.legend()
    plt.grid(True)
    plt.show()

def main():
    # Input della media e della deviazione standard della distribuzione normale
    mean = float(input("Inserisci la media della distribuzione: "))
    std_dev = math.sqrt(float(input("Inserisci la deviazione standard della distribuzione: ")))

    # Input degli estremi a e b della distribuzione normale
    print("Inserisci valore estremo inferiore a estremo superiore b")
    a = float(input("Inserisci il valore di a: "))
    b = float(input("Inserisci il valore di b: "))

    # Calcolo e visualizzazione della probabilità e dell'intera distribuzione normale
    calculate_and_plot_probability(a, b, mean, std_dev)

if __name__ == "__main__":
    main()
