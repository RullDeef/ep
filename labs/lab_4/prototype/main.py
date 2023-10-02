import numpy as np
import matplotlib.pyplot as plt
from model import y_func

from data_gen import load_data

def y_wrapper(x1, x2):
    y = y_func(x1, x2)
    return min(y, 50)

if __name__ == "__main__":
    # show 2d surface
    X1 = np.linspace(0.1, 3, 30)
    X2 = np.linspace(0.1, 3, 30)

    X1, X2 = np.meshgrid(X1, X2)
    # Y = np.vectorize(y_wrapper)(X1, X2)

    X1, X2, Y = load_data()

    # Y[X1 < 0.8] = 0
    # Y[X1 > 1.0] = 0
    # Y[X2 < 2] = 0
    # Y[X2 > 2.5] = 0
    Y[Y > 0.4] = 0

    # dydx1, dydx2 = np.gradient(Y)
    # G = np.hypot(dydx1, dydx2)

    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    ax.plot_surface(X1, X2, Y)

    ax.set_xlabel('x1')
    ax.set_ylabel('x2')
    ax.set_zlabel('grad')

    plt.show()
