from threading import Semaphore, Thread

#global variables
A: int = 0
B: int = 0
C: int = 3

semp1 = Semaphore(1)
semp2 = Semaphore(0)
semp3 = Semaphore(0)
semp4 = Semaphore(0)

def threadP1():
    semp1.acquire()
    global A
    global B
    global C
    A = 10
    semp2.release()
    semp1.acquire()
    B = B + 5
    C = C + A
    print("Thread P1 is done...")

def threadP2():
    semp2.acquire()
    global A
    global B
    global C
    B = B + C
    semp3.release()
    semp2.acquire()
    A = A + B
    print("Thread P2 is done...")

def threadP3():
    semp3.acquire()
    global A
    global B
    global C
    C = B + 10
    semp4.release()
    semp3.acquire()
    A = 2 * A
    B = B + A
    print("Thread P3 is done...")


def threadP4():
    semp4.acquire()
    global A
    global B
    global C
    print("Sum result: ",A," + ",B," + ",C," = ",(A + B + C))
    print("Thread P4 is done...")
    semp3.release()
    semp2.release()
    semp1.release()


threads = []
threads.append(Thread(target=threadP1))
threads.append(Thread(target=threadP2))
threads.append(Thread(target=threadP3))
threads.append(Thread(target=threadP4))

for thread in threads:
    thread.start()
for thread in threads:
    thread.join()
print("All done")