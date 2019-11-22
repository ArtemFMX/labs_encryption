#include <stdio.h>
#include <stdlib.h>
#include <locale.h>
#include <string.h>

int shifting(int code, int shift);
int encode(int shift, char *name1, char *name2);
void decodeOneSimbvol();
int analysis(char *name, float *table);

int main()
{
    setlocale(LC_CTYPE, "rus");

    int select, shift = -1;

        printf("1- encode file\n");
        printf("2- decode file\n");
        printf("0-Exit\n");
        printf("you choosed: ");
    do{
        switch (select) {
            case 1:

                while(shift < 0 || shift >32){
                        printf("Pls enter shift : ");
                        scanf("%d", &shift);
                }
                encode(shift, "glava1.txt", "glava1Cezar.txt");
                shift = -1;
                break;
            case 2:
                decodeOneSimbvol();
                break;
        }

    }while(select != 0 && scanf("%d", &select));

    return 0;
}

int encode(int shift, char *name1, char *name2){//зашифровка

    int count;
    char string[10788];

    FILE *glavaOne      = fopen(name1, "r");
    FILE *glavaOneCezar = fopen(name2, "w");

    if(glavaOne == NULL){
        printf("Error opening file");
		system("pause");
    } else {
        count = fread(string, 1, 10788, glavaOne);

        printf("encrypting...\n");

        for(int i = 0; i < 10788; i++){
            if((int)string[i] > -65 && (int)string[i] < 0){
                string[i] = (char)shifting((int)string[i], shift);
            } else {
                string[i] = string[i];
            }
        }

        printf("\nencrypted\n");
        fwrite(string, 1, count, glavaOneCezar);
        //fprintf(stdout, "Count of simbvols in file : %d, your shift = %d\n", count, shift);
    }

    fclose(glavaOne);
    fclose(glavaOneCezar);

    return 0;
}

void decodeOneSimbvol(){//посимвольная расшифровка
    //FILE *glavaOneCezar = fopen("glava1Cezar.txt", "r");
    float table1[32] = {0.0};
    float table2[32] = {0.0};

    int maxIndex1= -1, maxIndex2 = -1, shift = 0;
    float maxElem1 = -1, maxElem2 = -1;

    if(analysis("voyna-i-mir-tom-1.txt", table1) == 1 || analysis("glava1Cezar.txt", table2) == 1){
        return;
    }

    for(int i = 0; i < 32; i++){
        if(maxElem1 < table1[i]){
            maxIndex1 = i;
            maxElem1  = table1[i];
        }

        if(maxElem2 < table2[i]){
            maxIndex2 = i;
            maxElem2  = table2[i];
        }
    }

    shift = maxIndex1 + maxIndex2;
    printf("maxIndex1 = %d, maxElem1 = %f\n", maxIndex1, maxElem1);
    printf("maxIndex2 = %d, maxElem2 = %f\n", maxIndex2, maxElem2);
    printf("the file is decrypted, shift = %d\n", shift);
    if(shift > 0){
        encode(shift, "glava1Cezar.txt", "glava1Normal.txt");
    } else {
        encode(-shift, "glava1Cezar.txt", "glava1Normal.txt");
    }

    //вывусти таблицу частотного анализа первого тома и первого главы
    /*for(int i = 0; i < 32; i++){
        fprintf(stdout, "Simbvol : %c - %f || ", (char)(i-32), (table1[i]));
        fprintf(stdout, "Simbvol : %c - %f\n", (char)(i-32), (table2[i]));
    }*/
}

int analysis(char *name, float *table){//частотный анализ

    FILE *file = fopen(name, "r");

    int number = 0, count = 0;
    char line[100] = {0};

    if(file == NULL ){
        printf("Error opening file\n");
		system("pause");
		return 1;
    } else {

        while (fgets(line, 100, file)){
            for(int i = 0; i < 100; i++){
                number = (int)line[i];
                if(number > -65 && number < 0){
                    count++;
                    if(number >= -32){
                        table[number + 32]++;
                    } else {
                        table[number + 64]++;
                    }
                }
            }
        }
    }

    for(int i = 0; i < 32; i++){
        table[i] = table[i]/count;
    }

    fclose(file);
    return 0;
}

int shifting(int code, int shift){//сдвиг буквы, если сдвиг = 2, то а = в
    int newCode = code + shift;

    if(code < -33 && newCode > -33){
        newCode = newCode - 32;
    } else if(code > -33 && newCode > -1){
        newCode = newCode - 32;
    }

    return newCode;
}
