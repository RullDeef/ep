import pickle
import numpy as np
from model import y_func_builder

def save_data(Y):
    with open("Y.dat", "wb") as file:
        pickle.dump(Y, file)

def load_data() -> np.ndarray:
    with open("Y.dat", "rb") as file:
        return pickle.load(file)

if __name__ == "__main__":
    func = y_func_builder(100, 1000)

    X1 = np.linspace(0.1, 3, 50)
    X2 = np.linspace(0.1, 3, 50)

    X1, X2 = np.meshgrid(X1, X2)
    Y = np.vectorize(func)(X1, X2)

    save_data([X1, X2, Y])
